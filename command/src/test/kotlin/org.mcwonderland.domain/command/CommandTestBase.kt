package org.mcwonderland.domain.command

import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.fakes.UserStub

abstract class CommandTestBase {

    protected lateinit var command: Command

    protected lateinit var sender: UserStub

    @BeforeEach
    fun setupCommandTestBase() {
        sender = Dummies.createUserFullFilled()
    }

    fun executeCommand(exeString: String) {
        return executeCommand(exeString.split(" "))
    }

    fun executeWithNoArgs() {
        return executeCommand(emptyList())
    }

    fun executeCommand(args: List<String>) {
        return command.execute(CommandContextStub(sender, command.label, args))
    }


}