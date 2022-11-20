package org.mcwonderland.access

import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.UserRepository

class UserRepositoryImpl : UserRepository {

    override fun findUserByMcId(mcUUID: String): User? {
        TODO("Not yet implemented")
    }

    override fun findUserByDiscordId(discordId: String): User? {
        TODO("Not yet implemented")
    }

    override fun updateMcId(userId: String, mcId: String) {
        TODO("Not yet implemented")
    }

}