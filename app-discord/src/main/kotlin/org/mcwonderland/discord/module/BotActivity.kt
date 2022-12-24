package org.mcwonderland.discord.module

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Activity.watching

class BotActivity(private val jda: JDA) {

    fun setActivity(status: String) {
        jda.presence.setPresence(watching(status), false)
    }

}