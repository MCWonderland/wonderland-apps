package org.mcwonderland.domain.command.impl

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
    private val messages: Messages
) : Command {

    override fun execute(sender: User, args: List<String>): CommandResponse {
        val uuid = args.getOrNull(0) ?: return fail(messages.invalidArg("mcIgn"))

        return try {
            val userLinked = accountLinker.link(sender, uuid)
            ok(messages.linked(userLinked))
        } catch (e: AccountAlreadyLinkedException) {
            fail(messages.accountAlreadyLinked(e.linkedId))
        } catch (e: MCAccountNotFoundException) {
            fail(messages.mcAccountWithIgnNotFound(e.searchStr))
        } catch (e: MCAccountLinkedByOthersException) {
            fail(messages.targetAccountAlreadyLink(e.ign))
        }
    }

}
