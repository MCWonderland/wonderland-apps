package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.domain.commands.CommandToggleRegHandle

class ToggleRegHandleImpl(
    private val messages: Messages,
    private val commandHistory: CommandHistory
) : CommandToggleRegHandle<DiscordCommandContext> {

    override fun onEnableRegistrations(context: DiscordCommandContext) {
        commandHistory.sendEmbed(messages.nowAcceptRegistrations())
    }

    override fun onDisableRegistrations(context: DiscordCommandContext) {
        commandHistory.sendEmbed(messages.noLongerAcceptRegistrations())
    }

}
