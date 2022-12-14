package org.mcwonderland.domain.repository

import org.mcwonderland.domain.model.DiscordProfile
import org.mcwonderland.domain.model.User

interface UserRepository {
    fun findUpdated(profile: DiscordProfile): User

    fun findUserByMcId(mcUUID: String): User?
    fun findUserByDiscordId(discordId: String): User?
    fun findUsers(userIds: Collection<String>): Collection<User>
    fun updateMcId(userId: String, mcId: String): User?
}
