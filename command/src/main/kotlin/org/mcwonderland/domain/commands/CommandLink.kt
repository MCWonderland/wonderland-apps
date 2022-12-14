package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.exceptions.AccountAlreadyLinkedException
import org.mcwonderland.domain.exceptions.MCAccountLinkedByOthersException
import org.mcwonderland.domain.exceptions.MCAccountNotFoundException
import org.mcwonderland.domain.features.AccountLinker
import org.mcwonderland.domain.model.User

class CommandLink(
    override val label: String,
    private val accountLinker: AccountLinker,
    private val handle: CommandLinkHandle
) : Command {
    override val usage: String = "/$label <minecraft username>"

    override fun execute(sender: User, args: List<String>) {
        val uuid = args.getOrNull(0) ?: return handle.missingArgId()

        return try {
            val userLinked = accountLinker.link(sender, uuid)
            handle.linked(userLinked)
        } catch (e: AccountAlreadyLinkedException) {
            handle.accountAlreadyLinked(e)
        } catch (e: MCAccountNotFoundException) {
            handle.mcAccountNotFound(e)
        } catch (e: MCAccountLinkedByOthersException) {
            handle.mcAccountLinkedByOthers(e)
        }
    }

}

interface CommandLinkHandle {
    fun missingArgId()
    fun linked(userLinked: User)
    fun accountAlreadyLinked(e: AccountAlreadyLinkedException)
    fun mcAccountNotFound(e: MCAccountNotFoundException)
    fun mcAccountLinkedByOthers(e: MCAccountLinkedByOthersException)
}
