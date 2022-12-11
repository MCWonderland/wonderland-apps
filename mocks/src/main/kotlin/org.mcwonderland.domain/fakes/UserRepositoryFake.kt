package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.model.DiscordProfile
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.UserRepository

class UserRepositoryFake : UserRepository {

    private val users: MutableSet<User> = mutableSetOf()

    override fun findUpdated(profile: DiscordProfile): User {
        val user = findUserByDiscordId(profile.id) ?: run {
            val newUser = User(discordId = profile.id, discordUsername = profile.username)
            users.add(newUser)
            newUser
        }

        user.discordUsername = profile.username

        return user
    }


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

    fun insertUser(user: User): User {
        users.add(user)
        return user
    }

    override fun findUsers(userIds: Collection<String>): Collection<User> {
        return users.filter { userIds.contains(it.id) }
    }

    fun addUser(user: User) {
        this.users.add(user)
    }
}