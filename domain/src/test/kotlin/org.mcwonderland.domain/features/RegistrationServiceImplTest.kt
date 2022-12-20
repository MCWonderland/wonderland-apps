package org.mcwonderland.domain.features

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.domain.exceptions.NotAllowRegistrationsException
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.RequireLinkedAccountException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.fakes.*
import org.mcwonderland.domain.repository.SettingsRepository
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class RegistrationServiceImplTest {

    private lateinit var registrationService: RegistrationServiceImpl

    private lateinit var accountLinker: AccountLinkerFake
    private lateinit var registrationRepository: RegistrationRepositoryFake
    private lateinit var userRepository: UserRepositoryFake
    private lateinit var userFinder: UserFinderFake
    private lateinit var settingsRepository: SettingsRepository

    private lateinit var user: UserStub

    @BeforeEach
    fun setUp() {
        user = Dummies.createUserFullFilled()

        accountLinker = AccountLinkerFake()
        registrationRepository = RegistrationRepositoryFake()
        userRepository = UserRepositoryFake()
        settingsRepository = SettingsRepositoryFake()
        userFinder = UserFinderFake()

        registrationService =
            RegistrationServiceImpl(
                accountLinker,
                registrationRepository,
                settingsRepository,
                userRepository,
                userFinder
            )
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
        fun shouldReturnRegisteredUsers() {
            userRepository.insertUser(user)
            registrationRepository.addRegistration(user.id)

            user.addAdminPerm()

            val result = registrationService.listRegistrations()

            assertTrue { result.contains(user) }
        }
    }

    @Nested
    inner class ClearRegistrations {

        @Test
        fun shouldClearRegistrations() {
            registrationRepository.addRegistration(user.id)

            registrationService.clearRegistrations()

            assertFalse { registrationRepository.isRegistered(user.id) }
        }
    }

    @Nested
    inner class ToggleAllowRegistrations {


        @Test
        fun shouldToggle() {
            setAllowRegistrations(false)

            registrationService.toggleAllowRegistrations()

            assertTrue { settingsRepository.isAllowRegistrations() }
        }

    }

    @Nested
    inner class RemoveRegistration {
        @Test
        fun userNotFound_shouldFail() {
            assertThrows<UserNotFoundException> { registrationService.removeRegistration(user.id) }
        }

        @Test
        fun shouldRemoveRegistration() {
            registrationRepository.addRegistration(user.id)

            userFinder.add(user)
            registrationService.removeRegistration(user.id)

            assertFalse { registrationRepository.isRegistered(user.id) }
        }
    }


    private fun setAllowRegistrations(state: Boolean) {
        settingsRepository.setAllowRegistrations(state)
    }
}