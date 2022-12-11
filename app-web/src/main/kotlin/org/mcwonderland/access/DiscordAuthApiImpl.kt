package org.mcwonderland.access

import io.mokulu.discord.oauth.DiscordOAuth
import org.mcwonderland.domain.model.DiscordUser
import org.mcwonderland.domain.DiscordAuthApi

class DiscordAuthApiImpl(
    private val discordOAuth: DiscordOAuth,
    private val discordApiCreator: DiscordApiCreator
) : DiscordAuthApi {

    override fun findUserByCode(code: String): DiscordUser {
        val token = discordOAuth.getTokens(code)
        val discordApi = discordApiCreator.createDiscordApi(token.accessToken)
        val user = discordApi.fetchUser()

        return DiscordUser(
            id = user.id,
            email = user.email,
            username = user.username
        )
    }

}