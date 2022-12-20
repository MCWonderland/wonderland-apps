package org.mcwonderland.discord.module

import io.mockk.mockk
import io.mockk.verify
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Activity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BotActivityTest {
    private lateinit var jda: JDA
    private lateinit var botActivity: BotActivity

    @BeforeEach
    fun setUp() {
        jda = mockk(relaxed = true)
        botActivity = BotActivity(jda)
    }


    @Test
    fun testSetActivity() {
        botActivity.setActivity("test")
        verify { jda.presence.setPresence(Activity.watching("test"), false) }
    }


}