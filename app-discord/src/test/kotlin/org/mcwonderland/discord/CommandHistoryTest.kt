package org.mcwonderland.discord

import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CommandHistoryTest {

    private val commandHistory = CommandHistory()

    @Test
    fun shouldRecord() {
        val record = CommandRecord( channel = mockk(), user = mockk() )
        commandHistory.add(record)

        assertEquals(record, commandHistory.get())
    }

}