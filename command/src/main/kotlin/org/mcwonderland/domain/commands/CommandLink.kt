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
    private val handle: CommandLinkHandle<CommandContext>
) : Command {
    override val usage: String = "$label <minecraft username>"

    override fun execute(context: CommandContext) {
        val uuid = context.getArg(0) ?: return handle.missingArgId(context)

        return try {
            val userLinked = accountLinker.link(context.sender, uuid)
            handle.linked(context, userLinked)
        } catch (e: AccountAlreadyLinkedException) {
            handle.failAccountAlreadyLinked(context, e)
        } catch (e: MCAccountNotFoundException) {
            handle.failMcAccountNotFound(context, e)
        } catch (e: MCAccountLinkedByOthersException) {
            handle.failMcAccountLinkedByOthers(context, e)
        }
    }

}

interface CommandLinkHandle<Context : CommandContext> {
    fun missingArgId(context: Context)
    fun linked(context: Context, userLinked: User)
    fun failAccountAlreadyLinked(context: Context, e: AccountAlreadyLinkedException)
    fun failMcAccountNotFound(context: Context, e: MCAccountNotFoundException)
    fun failMcAccountLinkedByOthers(context: Context, e: MCAccountLinkedByOthersException)
}
