package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.domain.commands.CommandLinkHandle
import org.mcwonderland.domain.exceptions.AccountAlreadyLinkedException
import org.mcwonderland.domain.exceptions.MCAccountLinkedByOthersException
import org.mcwonderland.domain.exceptions.MCAccountNotFoundException
import org.mcwonderland.domain.model.User

class CommandLinkHandleImpl(
    private val messages: Messages,
    private val history: CommandHistory
) : CommandLinkHandle {

    override fun missingArgId() {
        history.createEmbed(messages.missingArg("mcIgn")).queue()
    }

    override fun linked(userLinked: User) {
        history.createEmbed(messages.linked(userLinked)).queue()
    }

    override fun failAccountAlreadyLinked(e: AccountAlreadyLinkedException) {
        history.createEmbed(messages.accountAlreadyLinked(e.linkedId)).queue()
    }

    override fun failMcAccountNotFound(e: MCAccountNotFoundException) {
        history.createEmbed(messages.mcAccountWithIgnNotFound(e.searchStr)).queue()
    }

    override fun failMcAccountLinkedByOthers(e: MCAccountLinkedByOthersException) {
        history.createEmbed(messages.targetAccountAlreadyLink(e.ign)).queue()
    }

}