package org.mcwonderland.domain.command

import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.fakes.UserStub

abstract class CommandTestBase {

    protected lateinit var command: Command

    protected lateinit var sender: UserStub
    protected lateinit var context: CommandContext

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
        context = CommandContextStub(sender, command.label, args)
        return command.execute(context)
    }

    fun assertUsageStartWithLabel() {
        assert(command.usage.startsWith(command.label))
    }

}