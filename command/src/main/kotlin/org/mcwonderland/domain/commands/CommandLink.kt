package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
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

    override fun execute(context: CommandContext) {
        val uuid = context.getArg(0) ?: return handle.missingArgId()

        return try {
            val userLinked = accountLinker.link(context.sender, uuid)
            handle.linked(userLinked)
        } catch (e: AccountAlreadyLinkedException) {
            handle.failAccountAlreadyLinked(e)
        } catch (e: MCAccountNotFoundException) {
            handle.failMcAccountNotFound(e)
        } catch (e: MCAccountLinkedByOthersException) {
            handle.failMcAccountLinkedByOthers(e)
        }
    }

}

interface CommandLinkHandle {
    fun missingArgId()
    fun linked(userLinked: User)
    fun failAccountAlreadyLinked(e: AccountAlreadyLinkedException)
    fun failMcAccountNotFound(e: MCAccountNotFoundException)
    fun failMcAccountLinkedByOthers(e: MCAccountLinkedByOthersException)
}
