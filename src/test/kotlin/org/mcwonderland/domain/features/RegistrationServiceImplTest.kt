package org.mcwonderland.domain.features

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.assertRuntimeError
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.config.MessagesStub
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.fakes.AccountLinkerFake
import org.mcwonderland.domain.fakes.RegistrationRepositoryFake
import org.mcwonderland.domain.fakes.UserRepositoryFake
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.RegistrationRepository
import org.mcwonderland.domain.repository.UserRepository
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class RegistrationServiceImplTest {

    private lateinit var registrationService: RegistrationServiceImpl

    private lateinit var accountLinker: AccountLinkerFake
    private lateinit var messages: Messages
    private lateinit var registrationRepository: RegistrationRepository
    private lateinit var userRepository: UserRepository

    private lateinit var user: User

    @BeforeEach
    fun setUp() {
        user = User("user_id")

        messages = MessagesStub()
        accountLinker = AccountLinkerFake()
        registrationRepository = RegistrationRepositoryFake()
        userRepository = UserRepositoryFake()
        registrationService = RegistrationServiceImpl(accountLinker, messages, registrationRepository, userRepository)
    }

    @Nested
    inner class ToggleRegister {
        @Test
        fun userWithoutLink_shouldCancel() {
            assertRuntimeError(messages.yourAccountNotLinked()) {
                registrationService.toggleRegister(user)
            }
        }

        @Test
        fun shouldRegister() {
            accountLinker.link(user, "link_id")

            registrationService.toggleRegister(user)

            assertTrue { registrationRepository.isRegistered(user.id) }
        }

        @Test
        fun alreadyRegistered_shouldRemoveRegistration() {
            accountLinker.link(user, "link_id")
            registrationRepository.addRegistration(user.id)

            val result = registrationService.toggleRegister(user)

            assertFalse { result }
            assertFalse { registrationRepository.isRegistered(user.id) }
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
}