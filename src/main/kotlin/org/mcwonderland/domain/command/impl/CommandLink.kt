package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.features.AccountLinker
import org.mcwonderland.domain.Messenger
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.getUuid
import org.mcwonderland.domain.model.PlatformUser

class CommandLink(
    override val label: String,
    private val accountLinker: AccountLinker,
    private val userFinder: UserFinder,
    private val messenger: Messenger
) : Command {

    override fun execute(sender: PlatformUser, args: List<String>) {

        try {
            val uuid = args.getUuid(0)
            accountLinker.link(userFinder.findOrCreate(sender.id), uuid.toString())
            messenger.sendMessage("Successfully linked your account to $uuid")
        } catch (e: Exception) {
            messenger.sendMessage(e.message ?: "Unknown error")
        }
    }

}
