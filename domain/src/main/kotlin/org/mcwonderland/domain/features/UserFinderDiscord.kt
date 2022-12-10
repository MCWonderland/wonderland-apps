package org.mcwonderland.domain.features

import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.UserRepository
import java.util.*

class UserFinderDiscord(
    private val userRepository: UserRepository,
) : UserFinder {
    override fun find(platformId: String): User? {
        return userRepository.findUserByDiscordId(platformId)
    }

    override fun findOrCreate(platformId: String): User {
        return find(platformId) ?: userRepository.insertUser(
            User(
                id = UUID.randomUUID().toString(),
                discordId = platformId
            )
        )
    }

}