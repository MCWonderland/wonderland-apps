package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.User

interface Command {
    val label: String
    val usage: String

    fun execute(sender: User, args: List<String>): CommandResponse

    fun ok(vararg message: String): CommandResponse {
        return CommandResponse(CommandStatus.SUCCESS, message.toList())
    }

    fun fail(vararg message: String): CommandResponse {
        return CommandResponse(CommandStatus.FAILURE, message.toList())
    }

    fun failWithUsage(): CommandResponse {
        return fail(this.usage)
    }
}