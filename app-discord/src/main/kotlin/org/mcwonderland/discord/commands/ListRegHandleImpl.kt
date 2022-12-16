package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.domain.commands.CommandListRegHandle
import org.mcwonderland.domain.model.User

class ListRegHandleImpl(
    private val messages: Messages,
) : CommandListRegHandle<DiscordCommandContext> {

    override fun failNoPermission(context: DiscordCommandContext) {
        context.sendEmbed(messages.noPermission())
    }

    override fun success(context: DiscordCommandContext, users: Collection<User>) {
        context.sendEmbed(messages.listRegistrations(users))
    }

}
