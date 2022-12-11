package org.mcwonderland.domain.access

import io.mokulu.discord.oauth.DiscordAPI

class DiscordApiCreator {
    fun createDiscordApi(accessToken: String): DiscordAPI {
        return DiscordAPI(accessToken)
    }
}