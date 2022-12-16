package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.domain.commands.CommandRegisterHandle
import org.mcwonderland.domain.exceptions.NotAllowRegistrationsException
import org.mcwonderland.domain.exceptions.RequireLinkedAccountException

class RegisterHandleImpl(
    private val messages: Messages,
    private val commandHistory: CommandHistory
) : CommandRegisterHandle<DiscordCommandContext> {

    override fun onRegistered(context: DiscordCommandContext) {
        commandHistory.sendEmbed(messages.registered())
    }

    override fun onUnregistered(context: DiscordCommandContext) {
        commandHistory.sendEmbed(messages.unRegistered())
    }

    override fun failRequireLinkedAccount(context: DiscordCommandContext, e: RequireLinkedAccountException) {
        commandHistory.sendEmbed(messages.requireLinkedAccount())
    }

    override fun failNotAllowRegistrations(context: DiscordCommandContext, e: NotAllowRegistrationsException) {
        commandHistory.sendEmbed(messages.notAllowRegistrations())
    }

}
