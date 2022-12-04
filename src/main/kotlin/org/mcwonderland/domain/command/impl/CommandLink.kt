package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.features.AccountLinker
import org.mcwonderland.discord.Messenger
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.command.CommandStatus
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.model.PlatformUser
import java.lang.RuntimeException

class CommandLink(
    override val label: String,
    private val accountLinker: AccountLinker,
    private val userFinder: UserFinder,
    private val messages: Messages
) : Command {

    override fun execute(sender: PlatformUser, args: List<String>): CommandResponse {
        val uuid = args.getOrNull(0) ?: return CommandResponse(CommandStatus.FAILURE, listOf(messages.invalidArg("mcIgn")))
        val userLinked = accountLinker.link(userFinder.findOrCreate(sender.id), uuid)

        return CommandResponse(CommandStatus.SUCCESS, listOf(messages.linked(userLinked)))
    }

}
