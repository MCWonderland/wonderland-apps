package org.mcwonderland.domain

import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.UserRepository
import java.util.UUID

class UserFinderByDiscordId(
    private val userRepository: UserRepository,
) : UserFinder {
    override fun find(platformId: String): User? {
        TODO("Not yet implemented")
    }

    override fun findOrCreate(platformId: String): User {
        val user = userRepository.findUserByDiscordId(platformId)
        return user ?: userRepository.insertUser(User(id = UUID.randomUUID().toString(), discordId = platformId))
    }

}