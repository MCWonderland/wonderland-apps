package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.domain.commands.CommandRemoveRegHandle
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.model.User

class RemoveRegHandle(private val messages: Messages) : CommandRemoveRegHandle<DiscordCommandContext> {

    override fun failPermissionDenied(context: DiscordCommandContext, e: PermissionDeniedException) {
        context.sendEmbed(messages.noPermission())
    }

    override fun failWithUsage(context: DiscordCommandContext, usage: String) {
        context.sendEmbed(messages.commandUsage(usage))
    }

    override fun registrationRemoved(context: DiscordCommandContext, target: User) {
        context.sendEmbed(messages.registrationRemoved(target))
    }
}