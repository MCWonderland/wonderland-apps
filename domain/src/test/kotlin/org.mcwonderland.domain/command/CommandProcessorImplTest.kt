package org.mcwonderland.domain.command

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.config.MessagesStub
import org.mcwonderland.domain.fakes.Dummies
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CommandProcessorImplTest {

    private lateinit var command: Command
    private lateinit var processer: CommandProcessor
    private lateinit var messages: Messages

    private val user = Dummies.createUserFullFilled()

    @BeforeEach
    fun setUp() {
        command = mockk(relaxed = true) { every { label } returns "test" }
        messages = MessagesStub()
        processer = CommandProcessorImpl(listOf(command), messages)
    }

    @Test
    fun shouldForwardToCorrectCommand() {
        processer.onCommand(user, "test", listOf("arg"))
        verify(exactly = 1) { command.execute(user, listOf("arg")) }
    }

    @Test
    fun onCommandThrowsException_shouldReturnFail() {
        val exception = Exception("This error msg should not be included")

        every { command.execute(user, listOf("arg")) } throws exception

        processer.onCommand(user, "test", listOf("arg"))!!.let {
            assertEquals(CommandStatus.FAILURE, it.status)
            assertEquals(messages.unHandledCommandError(exception::class.java.simpleName), it.firstMessage)
        }
    }


}