package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.AccountLinker
import org.mcwonderland.domain.MessageSender
import org.mcwonderland.domain.UserFinder
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.getUuid
import org.mcwonderland.domain.exception.AccountAlreadyOwnedException
import org.mcwonderland.domain.exception.AccountNotExistException
import org.mcwonderland.domain.exception.AlreadyLinkedException
import org.mcwonderland.domain.model.CommandSender
import org.mcwonderland.domain.repository.UserRepository

class CommandLink(
    override val label: String,
    private val accountLinker: AccountLinker,
    private val userFinder: UserFinder,
    private val messageSender: MessageSender
) : Command {

    override fun execute(sender: CommandSender, args: List<String>) {
        val uuid = args.getUuid(0)

        try {
            accountLinker.link(userFinder.findOrCreate(sender.id), uuid.toString())
        } catch (e: Exception) {
            messageSender.sendMessage(sender.id, e.message ?: "Unknown error")
        }
    }

}
