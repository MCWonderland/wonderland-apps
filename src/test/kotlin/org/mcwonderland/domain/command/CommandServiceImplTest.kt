package org.mcwonderland.domain.command

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.mcwonderland.domain.model.User
import kotlin.test.Test

internal class CommandServiceImplTest {

    @Test
    fun shouldForwardToCorrectCommand() {
        val user = User("user_id")
        val command = mockk<Command>(relaxed = true) { every { label } returns "test" }
        val commandService = CommandServiceImpl(listOf(command))

        commandService.onCommand(user, "test", listOf("arg"))

        verify(exactly = 1) { command.execute(user, listOf("arg")) }
    }

}