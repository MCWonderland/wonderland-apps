package org.mcwonderland.domain.command

import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.config.MessagesStub
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.fakes.UserStub

abstract class CommandTestBase {

    protected lateinit var command: Command
    protected lateinit var messages: Messages
        private set

    protected lateinit var sender: UserStub

    @BeforeEach
    fun setupCommandTestBase() {
        sender = Dummies.createUserFullFilled()
        messages = MessagesStub()
    }

    fun executeCommand(exeString: String) {
        return command.execute(sender)
    }

    fun executeWithNoArgs() {
        return command.execute(sender)
    }

    fun executeCommand(args: List<String>) {
        return command.execute(sender)
    }


}