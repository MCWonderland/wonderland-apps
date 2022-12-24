package org.mcwonderland.domain.commands

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.features.RegistrationService

internal class CommandListRegTest : CommandTestBase() {

    private lateinit var handle: CommandListRegHandle<CommandContext>
    private lateinit var registrationService: RegistrationService

    @BeforeEach
    fun setup() {
        registrationService = mockk(relaxed = true)
        handle = mockk(relaxed = true)
        command = CommandListReg("listreg", registrationService, handle)
    }

    @Test
    fun wihtoutPerm_shouldFail() {
        executeCommand("listreg").also { verify { handle.failNoPermission(context) } }
    }

    @Test
    fun shouldCallService() {
        val expectUsers = listOf(Dummies.createUserFullFilled(), Dummies.createUserFullFilled())
        sender.addAdminPerm()
        every { registrationService.listRegistrations() } returns expectUsers

        executeWithNoArgs().also {
            verify { handle.success(context, expectUsers) }
        }
    }

}