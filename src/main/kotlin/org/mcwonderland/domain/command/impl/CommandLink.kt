package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.features.AccountLinker
import org.mcwonderland.domain.Messenger
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.getUuid
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.model.PlatformUser
import java.lang.RuntimeException

class CommandLink(
    override val label: String,
    private val accountLinker: AccountLinker,
    private val userFinder: UserFinder,
    private val messenger: Messenger,
    private val messages: Messages
) : Command {

    override fun execute(sender: PlatformUser, args: List<String>) = runCommand(messenger) {
        val uuid = args.getOrNull(0) ?: throw RuntimeException(messages.invalidArg("mcIgn"))
        val userLinked = accountLinker.link(userFinder.findOrCreate(sender.id), uuid.toString())
        messenger.sendMessage(messages.linked(userLinked))
    }

    private fun fail(message: String) {
        throw RuntimeException(message)
    }

}
