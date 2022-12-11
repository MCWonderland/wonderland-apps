package org.mcwonderland.domain.service

import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.User

class AuthService(
    private val discordOAuth: DiscordOAuth,
    private val userFinder: UserFinder
) {

    fun login(code: String): User {
        val discordUser = discordOAuth.findUserByCode(code)
        return userFinder.findOrCreate(discordUser.id)
    }

}