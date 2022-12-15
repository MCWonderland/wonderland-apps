package org.mcwonderland.discord

import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.discord.module.CommandHistoryImpl
import org.mcwonderland.discord.module.CommandRecord

class CommandHistoryImplTest {

    private val commandHistory = CommandHistoryImpl()

    @Test
    fun shouldRecord() {
        val record = CommandRecord( channel = mockk(), user = mockk() )
        commandHistory.add(record)

        assertEquals(record, commandHistory.get())
    }

}