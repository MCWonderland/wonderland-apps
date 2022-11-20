package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.getUuid
import org.mcwonderland.domain.model.CommandSender

class CommandLink(
    override val label: String,
    private val accountLinker: AccountLinker
) : Command {

    override fun execute(sender: CommandSender, args: List<String>) {
        val uuid = args.getUuid(0)
        accountLinker.link(sender, uuid.toString())
    }

}
