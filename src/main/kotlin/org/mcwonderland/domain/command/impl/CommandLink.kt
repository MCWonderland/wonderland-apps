package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.command.CommandStatus
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.AccountLinker
import org.mcwonderland.domain.model.User

class CommandLink(
    override val label: String,
    private val accountLinker: AccountLinker,
    private val messages: Messages
) : Command {

    override fun execute(sender: User, args: List<String>): CommandResponse {
        val uuid =
            args.getOrNull(0) ?: return CommandResponse(CommandStatus.FAILURE, listOf(messages.invalidArg("mcIgn")))
        val userLinked = accountLinker.link(sender, uuid)

        return CommandResponse(CommandStatus.SUCCESS, listOf(messages.linked(userLinked)))
    }

}
