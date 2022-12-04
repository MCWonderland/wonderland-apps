package org.mcwonderland.domain.command

import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.config.MessagesStub
import org.mcwonderland.domain.fakes.UserFinderStub
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser
import org.mcwonderland.domain.model.User
import kotlin.test.assertEquals

abstract class CommandTestBase {

    protected lateinit var command: Command
    protected lateinit var messages: Messages
        private set
    protected lateinit var userFinder: UserFinder
        private set

    protected val sender = PlatformUser("sender")
    protected val user = User("user")

    @BeforeEach
    fun setupCommandTestBase() {
        messages = MessagesStub()
        userFinder = UserFinderStub(user)
    }

    fun executeCommand(exeString: String): CommandResponse {
        return command.execute(sender, exeString.split(" "))
    }

    fun executeWithNoArgs(): CommandResponse {
        return command.execute(sender, listOf())
    }

    fun executeCommand(args: List<String>): CommandResponse {
        return command.execute(sender, args)
    }


    fun CommandResponse.assertFail(message: String) {
        assertEquals(message, messages.last())
        assertEquals(CommandStatus.FAILURE, status)
    }

    fun CommandResponse.assertSuccess(message: String) {
        assertEquals(CommandStatus.SUCCESS, status)
    }

}