package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.model.User

class CommandStub(override val label: String) : Command {
    override val usage: String = "/$label"

    override fun execute(sender: User, args: List<String>): CommandResponse {
        return ok()
    }
}