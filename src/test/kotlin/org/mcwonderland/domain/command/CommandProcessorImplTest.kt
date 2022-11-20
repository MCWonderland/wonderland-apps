package org.mcwonderland.domain.command

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.mcwonderland.domain.fakes.Dummies
import kotlin.test.Test

internal class CommandProcessorImplTest {

    @Test
    fun shouldForwardToCorrectCommand() {
        val commandSender = Dummies.createCommandSender()
        val command = mockk<Command>(relaxed = true) { every { label } returns "test" }
        val commandService = CommandProcessorImpl(listOf(command))

        commandService.onCommand(commandSender, "test", listOf("arg"))

        verify(exactly = 1) { command.execute(commandSender, listOf("arg")) }
    }

}