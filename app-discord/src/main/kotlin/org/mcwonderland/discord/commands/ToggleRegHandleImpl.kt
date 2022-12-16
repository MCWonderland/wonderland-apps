package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.domain.commands.CommandToggleRegHandle

class ToggleRegHandleImpl(
    private val messages: Messages,
    private val commandHistory: CommandHistory
) : CommandToggleRegHandle<Any?> {

    override fun onEnableRegistrations() {
        commandHistory.sendEmbed(messages.nowAcceptRegistrations())
    }

    override fun onDisableRegistrations() {
        commandHistory.sendEmbed(messages.noLongerAcceptRegistrations())
    }

}
