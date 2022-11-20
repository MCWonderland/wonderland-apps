package org.mcwonderland.domain.command

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.mcwonderland.domain.model.CommandSender
import kotlin.test.Test

internal class CommandServiceImplTest {

    @Test
    fun shouldForwardToCorrectCommand() {
        val commandSender = CommandSender("user_id")
        val command = mockk<Command>(relaxed = true) { every { label } returns "test" }
        val commandService = CommandServiceImpl(listOf(command))

        commandService.onCommand(commandSender, "test", listOf("arg"))

        verify(exactly = 1) { command.execute(commandSender, listOf("arg")) }
    }

}