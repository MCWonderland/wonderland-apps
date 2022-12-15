package org.mcwonderland.discord.module

import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction
import org.mcwonderland.domain.model.User

interface CommandHistory {
    fun add(record: CommandRecord)
    fun get(): CommandRecord

    fun createEmbed(embed: MessageEmbed): MessageCreateAction {
        return get().channel.sendMessageEmbeds(embed)
    }

    fun createMessge(message: String): MessageCreateAction {
        return get().channel.sendMessage(message)
    }
}

class CommandHistoryImpl : CommandHistory {

    private var lastRecord: CommandRecord? = null

    override fun add(record: CommandRecord) {
        lastRecord = record
    }

    override fun get(): CommandRecord {
        return lastRecord ?: throw IllegalStateException("No command record found")
    }

}


data class CommandRecord(val channel: MessageChannelUnion, val user: User) {

    fun sendEmbed(embed: MessageEmbed) {

    }

}