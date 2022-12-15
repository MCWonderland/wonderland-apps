package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.domain.commands.CommandListRegHandle
import org.mcwonderland.domain.model.User

class ListRegHandleImpl(
    private val messages: Messages,
    private val commandHistory: CommandHistory
) : CommandListRegHandle {

    override fun failNoPermission() {
        commandHistory.sendEmbed(messages.noPermission())
    }

    override fun success(users: Collection<User>) {
        commandHistory.sendEmbed(messages.listRegistrations(users))
    }

}
