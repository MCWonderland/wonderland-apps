package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.domain.commands.CommandRegisterHandle
import org.mcwonderland.domain.exceptions.NotAllowRegistrationsException
import org.mcwonderland.domain.exceptions.RequireLinkedAccountException

class RegisterHandleImpl(private val messages: Messages) : CommandRegisterHandle<DiscordCommandContext> {

    override fun onRegistered(context: DiscordCommandContext) {
        context.sendEmbed(messages.registered())
    }

    override fun onUnregistered(context: DiscordCommandContext) {
        context.sendEmbed(messages.unRegistered())
    }

    override fun failRequireLinkedAccount(context: DiscordCommandContext, e: RequireLinkedAccountException) {
        context.sendEmbed(messages.requireLinkedAccount())
    }

    override fun failNotAllowRegistrations(context: DiscordCommandContext, e: NotAllowRegistrationsException) {
        context.sendEmbed(messages.notAllowRegistrations())
    }

}
