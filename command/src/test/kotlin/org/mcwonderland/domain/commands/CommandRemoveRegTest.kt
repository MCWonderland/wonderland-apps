package org.mcwonderland.domain.commands

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.features.RegistrationService

class CommandRemoveRegTest : CommandTestBase() {

    private lateinit var handle: CommandRemoveRegHandle<CommandContext>
    private lateinit var registrationService: RegistrationService

    @BeforeEach
    fun setup() {
        handle = mockk(relaxed = true)
        registrationService = mockk(relaxed = true)
        command = CommandRemoveReg("removereg", handle, registrationService)
    }

    @Test
    fun withoutPerms_shouldDeny() {
        executeWithNoArgs().also { verify { handle.failPermissionDenied(context, any()) } }
    }

    @Test
    fun withoutArgs_shouldShowUsage() {
        sender.addAdminPerm()
        executeWithNoArgs().also { verify { handle.failWithUsage(context, command.usage) } }
    }

    @Test
    fun testExceptionMapping() {
        assertExceptionMapping(UserNotFoundException("player")) {
            handle.failUserNotFound(context, it)
        }
    }


    private fun <T : Exception> assertExceptionMapping(exception: T, block: (T) -> Unit) {
        every { registrationService.removeRegistration(any()) } throws exception
        sender.addAdminPerm()
        executeCommand("player")
        verify { block(exception) }
    }


    @Test
    fun shouldCallService() {
        val target = Dummies.createUserFullFilled()
        sender.addAdminPerm()
        every { registrationService.removeRegistration("player") } returns target

        executeCommand("player")
            .also {
                verify { handle.registrationRemoved(context, target) }
            }
    }


}