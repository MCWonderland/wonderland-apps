package org.mcwonderland.access

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Filters.`in`
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Updates
import com.mongodb.client.model.Updates.*
import org.mcwonderland.domain.model.DiscordProfile
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.UserRepository
import java.util.UUID

class UserRepositoryImpl(
    private val mongoClient: MongoClient,
    private val dbName: String,
) : UserRepository {

    private val db = mongoClient.getDatabase(dbName)
    private val userCollection = db.getUserCollection()

    override fun findUpdated(profile: DiscordProfile): User {
        return userCollection.findOneAndUpdate(
            eq(MongoUser::discordId.name, profile.id),
            combine(
                setOnInsert("_id", UUID.randomUUID().toString()),
                setOnInsert(MongoUser::discordId.name, profile.id),
                set(MongoUser::discordUsername.name, profile.username),
            ),
            FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER)
        )!!
    }

    override fun findUserByMcId(mcUUID: String): User? {
        return userCollection.find(eq(MongoUser::mcId.name, mcUUID)).first()
    }

    override fun findUserByDiscordId(discordId: String): User? {
        return userCollection.find(eq(MongoUser::discordId.name, discordId)).first()
    }

    override fun updateMcId(userId: String, mcId: String): User? {
        return userCollection.findOneAndUpdate(
            eq("_id", userId),
            set(MongoUser::mcId.name, mcId),
            FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        )
    }

    override fun findUsers(userIds: Collection<String>): Collection<User> {
        return userCollection.find(`in`("_id", userIds)).toList()
    }

}