package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.domain.commands.CommandRegisterHandle
import org.mcwonderland.domain.exceptions.NotAllowRegistrationsException
import org.mcwonderland.domain.exceptions.RequireLinkedAccountException

class CommandRegisterHandleImpl(
    private val messages: Messages,
    private val commandHistory: CommandHistory
) : CommandRegisterHandle {

    override fun onRegistered() {
        commandHistory.sendEmbed(messages.registered())
    }

    override fun onUnregistered() {
        commandHistory.sendEmbed(messages.unRegistered())
    }

    override fun failRequireLinkedAccount(e: RequireLinkedAccountException) {
        commandHistory.sendEmbed(messages.requireLinkedAccount())
    }

    override fun failNotAllowRegistrations(e: NotAllowRegistrationsException) {
        commandHistory.sendEmbed(messages.notAllowRegistrations())
    }

}
