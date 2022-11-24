package org.mcwonderland.access

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters.eq
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.UserRepository

class UserRepositoryImpl(
    private val mongoClient: MongoClient,
    private val config: Config
) : UserRepository {

    override fun findUserByMcId(mcUUID: String): User? {
        return mongoClient.getDatabase(config.dbName)
            .getUserCollection()
            .find(eq(User::mcId.name, mcUUID))
            .first()
    }

    override fun findUserByDiscordId(discordId: String): User? {
        TODO("Not yet implemented")
    }

    override fun updateMcId(userId: String, mcId: String) {
        TODO("Not yet implemented")
    }

}