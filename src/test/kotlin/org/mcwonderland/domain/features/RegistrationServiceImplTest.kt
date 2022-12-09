package org.mcwonderland.domain.features

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.domain.exceptions.NotAllowRegistrationsException
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.RequireLinkedAccountException
import org.mcwonderland.domain.fakes.AccountLinkerFake
import org.mcwonderland.domain.fakes.RegistrationRepositoryFake
import org.mcwonderland.domain.fakes.SettingsRepositoryFake
import org.mcwonderland.domain.fakes.UserRepositoryFake
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.SettingsRepository
import org.mcwonderland.domain.repository.UserRepository
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class RegistrationServiceImplTest {

    private lateinit var registrationService: RegistrationServiceImpl

    private lateinit var accountLinker: AccountLinkerFake
    private lateinit var registrationRepository: RegistrationRepositoryFake
    private lateinit var userRepository: UserRepository
    private lateinit var settingsRepository: SettingsRepository

    private lateinit var user: User

    @BeforeEach
    fun setUp() {
        user = User("user_id")

        accountLinker = AccountLinkerFake()
        registrationRepository = RegistrationRepositoryFake()
        userRepository = UserRepositoryFake()
        settingsRepository = SettingsRepositoryFake()
        registrationService =
            RegistrationServiceImpl(accountLinker, registrationRepository, settingsRepository, userRepository)
    }

    @Nested
    inner class ToggleRegister {
        @Test
        fun userWithoutLink_shouldCancel() {
            assertThrows<RequireLinkedAccountException> { registrationService.toggleRegister(user) }
        }

        @Test
        fun notAllowRegistrations_shouldCancel() {
            doLink()
            settingsRepository.setAllowRegistrations(false)

            assertThrows<NotAllowRegistrationsException> { registrationService.toggleRegister(user) }
        }

        @Test
        fun shouldRegister() {
            doLink()

            setAllowRegistrations(true)
            registrationService.toggleRegister(user)

            assertTrue { registrationRepository.isRegistered(user.id) }
        }

        @Test
        fun alreadyRegistered_shouldRemoveRegistration() {
            doLink()
            registrationRepository.addRegistration(user.id)
            setAllowRegistrations(true)

            val result = registrationService.toggleRegister(user)

            assertFalse { result }
            assertFalse { registrationRepository.isRegistered(user.id) }
        }

        private fun doLink() {
            accountLinker.link(user, "link_id")
        }
    }

    @Nested
    inner class ListRegistrations {

        @Test
        fun withoutPermission_shouldDenied() {
            assertThrows<PermissionDeniedException> { registrationService.listRegistrations(user) }
        }

        @Test
        fun shouldReturnRegisteredUsers() {
            userRepository.insertUser(user)
            registrationRepository.addRegistration(user.id)

            user.addAdminPerm()

            val result = registrationService.listRegistrations(user)

            assertTrue { result.contains(user) }
        }
    }

    @Nested
    inner class ClearRegistrations {
        @Test
        fun withoutPermission_shouldDenied() {
            assertThrows<PermissionDeniedException> { registrationService.clearRegistrations(user) }
        }

        @Test
        fun shouldClearRegistrations() {
            user.addAdminPerm()
            registrationRepository.addRegistration(user.id)

            registrationService.clearRegistrations(user)

            assertFalse { registrationRepository.isRegistered(user.id) }
        }
    }

    @Nested
    inner class ToggleAllowRegistrations {

        @Test
        fun withoutPermission_shouldDenied() {
            assertThrows<PermissionDeniedException> { registrationService.toggleAllowRegistrations(user) }
        }

        @Test
        fun shouldToggle() {
            user.addAdminPerm()
            setAllowRegistrations(false)

            registrationService.toggleAllowRegistrations(user)

            assertTrue { settingsRepository.isAllowRegistrations() }
        }

    }


    private fun setAllowRegistrations(state: Boolean) {
        settingsRepository.setAllowRegistrations(state)
    }
}