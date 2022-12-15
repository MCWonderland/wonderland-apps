package org.mcwonderland.discord.module

class CommandHistoryStub(private val record: CommandRecord) : CommandHistory {

    override fun add(record: CommandRecord) {}

    override fun get(): CommandRecord {
        return record
    }
}