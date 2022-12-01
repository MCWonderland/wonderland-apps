package org.mcwonderland.domain.command

import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.config.MessagesStub
import org.mcwonderland.domain.fakes.MessengerFake
import org.mcwonderland.domain.fakes.UserFinderStub
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser
import org.mcwonderland.domain.model.User

abstract class CommandTestBase {

    protected lateinit var command: Command

    protected lateinit var messenger: MessengerFake
        private set
    protected lateinit var messages: Messages
        private set
    protected lateinit var userFinder: UserFinder
        private set

    protected val sender = PlatformUser("sender")
    protected val user = User("user")

    @BeforeEach
    fun setupCommandTestBase() {
        messenger = MessengerFake()
        messages = MessagesStub()
        userFinder = UserFinderStub(user)
    }

    fun executeCommand(exeString: String) {
        command.execute(sender, exeString.split(" "))
    }

    fun executeWithNoArgs() {
        command.execute(sender, listOf())
    }

    fun executeCommand(args: List<String>) {
        command.execute(sender, args)
    }
}