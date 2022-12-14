package org.mcwonderland.access

import io.mokulu.discord.oauth.DiscordAPI

class DiscordApiCreator {
    fun createDiscordApi(accessToken: String): DiscordAPI {
        return DiscordAPI(accessToken)
    }
}