package org.mcwonderland.discord

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import org.mcwonderland.domain.model.User

class CommandHistory {

    private var lastRecord: CommandRecord? = null

    fun add(record: CommandRecord) {
        lastRecord = record
    }

    fun get(): CommandRecord {
        return lastRecord ?: throw IllegalStateException("No command record found")
    }

}


data class CommandRecord(
    val channel: MessageChannelUnion,
    val user: User
)