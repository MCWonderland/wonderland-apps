package org.mcwonderland.access

import io.mokulu.discord.oauth.DiscordOAuth
import io.mokulu.discord.oauth.model.TokensResponse
import org.jsoup.HttpStatusException
import org.mcwonderland.domain.model.DiscordUser
import org.mcwonderland.domain.DiscordAuthApi
import org.mcwonderland.domain.exceptions.InvalidOAuthException

class DiscordAuthApiImpl(
    private val discordOAuth: DiscordOAuth,
    private val discordApiCreator: DiscordApiCreator
) : DiscordAuthApi {

    override fun findUserByCode(code: String): DiscordUser {
        val token = getToken(code)
        val discordApi = discordApiCreator.createDiscordApi(token.accessToken)
        val user = discordApi.fetchUser()

        return DiscordUser(
            id = user.id,
            email = user.email,
            username = user.username
        )
    }

    private fun getToken(code: String): TokensResponse {
        return try {
            discordOAuth.getTokens(code)
        } catch (e: HttpStatusException) {
            throw InvalidOAuthException()
        }
    }

}