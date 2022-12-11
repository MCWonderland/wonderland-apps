package org.mcwonderland.domain.features

import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.UserRepository

class UserFinderDiscord(private val userRepository: UserRepository) : UserFinder {
    override fun find(platformId: String): User? {
        return userRepository.findUserByDiscordId(platformId)
    }
}