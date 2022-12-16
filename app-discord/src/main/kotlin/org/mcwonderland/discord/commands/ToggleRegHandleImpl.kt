package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.domain.commands.CommandToggleRegHandle

class ToggleRegHandleImpl(private val messages: Messages) : CommandToggleRegHandle<DiscordCommandContext> {

    override fun onEnableRegistrations(context: DiscordCommandContext) {
        context.sendEmbed(messages.nowAcceptRegistrations())
    }

    override fun onDisableRegistrations(context: DiscordCommandContext) {
        context.sendEmbed(messages.noLongerAcceptRegistrations())
    }

}
