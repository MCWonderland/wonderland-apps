package org.mcwonderland.domain.command

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.domain.fakes.Dummies
import kotlin.test.Test

internal class CommandProcessorImplTest {

    private lateinit var command: Command
    private lateinit var processer: CommandProcessor

    private val user = Dummies.createUserFullFilled()

    @BeforeEach
    fun setUp() {
        command = mockk(relaxed = true) { every { label } returns "test" }
        processer = CommandProcessorImpl(listOf(command))
    }

    @Test
    fun shouldForwardToCorrectCommand() {
        processer.onCommand(user, "test", listOf("arg"))
        verify(exactly = 1) { command.execute(user) }
    }

}