package org.mcwonderland.domain.service

import org.mcwonderland.domain.DiscordAuthApi
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.DiscordProfile
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.UserRepository

class AuthService(
    private val discordOAuth: DiscordAuthApi,
    private val userRepository: UserRepository,
) {

    fun login(code: String): User {
        val discordUser = discordOAuth.findUserByCode(code)
        return userRepository.findUpdated(DiscordProfile(discordUser.id, discordUser.username))
    }

}