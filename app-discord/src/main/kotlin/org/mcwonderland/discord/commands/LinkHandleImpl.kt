package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.domain.commands.CommandLinkHandle
import org.mcwonderland.domain.exceptions.AccountAlreadyLinkedException
import org.mcwonderland.domain.exceptions.MCAccountLinkedByOthersException
import org.mcwonderland.domain.exceptions.MCAccountNotFoundException
import org.mcwonderland.domain.model.User

class LinkHandleImpl(
    private val messages: Messages,
) : CommandLinkHandle<DiscordCommandContext> {

    override fun missingArgId(context: DiscordCommandContext) {
        context.sendEmbed(messages.missingArg("mcIgn"))
    }

    override fun linked(context: DiscordCommandContext, userLinked: User) {
        context.sendEmbed(messages.linked(userLinked))
    }

    override fun failAccountAlreadyLinked(context: DiscordCommandContext, e: AccountAlreadyLinkedException) {
        context.sendEmbed(messages.accountAlreadyLinked(e.linkedId))
    }

    override fun failMcAccountNotFound(context: DiscordCommandContext, e: MCAccountNotFoundException) {
        context.sendEmbed(messages.mcAccountWithIgnNotFound(e.searchStr))
    }

    override fun failMcAccountLinkedByOthers(context: DiscordCommandContext, e: MCAccountLinkedByOthersException) {
        context.sendEmbed(messages.targetAccountAlreadyLink(e.ign))
    }

}