package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.UserRepository

class UserRepositoryFake : UserRepository {

    private val users: MutableSet<User> = mutableSetOf()

    override fun findUserByMcId(mcUUID: String): User? {
        return users.find { it.mcId == mcUUID }
    }

    override fun findUserByDiscordId(discordId: String): User? {
        return users.find { it.discordId == discordId }
    }

    override fun updateMcId(userId: String, mcId: String): User? {
        val user = users.find { it.id == userId } ?: return null
        user.mcId = mcId
        return user
    }

    fun addUser(user: User) {
        this.users.add(user)
    }
}