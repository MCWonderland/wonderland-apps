package org.mcwonderland.domain.repository

import org.mcwonderland.domain.model.User

interface UserRepository {
    fun findUserByMcId(mcUUID: String): User?
    fun findUserByDiscordId(discordId: String): User?
    fun updateMcId(userId: String, mcId: String)
}
