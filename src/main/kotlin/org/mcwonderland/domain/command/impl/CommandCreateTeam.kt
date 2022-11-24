package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.Messenger
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.model.CommandSender

class CommandCreateTeam(
    override val label: String,
    private val messenger: Messenger
) : Command {

    override val usage: String = "Usage: /$label <id> <id>...."

    override fun execute(sender: CommandSender, args: List<String>) {
        if (args.isEmpty()) {
            messenger.sendMessage(usage)
            return
        }

    }


}