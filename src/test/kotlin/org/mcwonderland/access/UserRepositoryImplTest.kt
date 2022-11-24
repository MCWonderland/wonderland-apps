package org.mcwonderland.access

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.fakes.ConfigStub
import org.mcwonderland.domain.model.User
import kotlin.test.assertEquals

internal class UserRepositoryImplTest : MongoDBTest() {

    private lateinit var config: Config
    private lateinit var userRepository: UserRepositoryImpl

    private val user = User(id = "123", mcId = "mc_id", discordId = "discord_id")

    private val userCollection
        get() = mongoClient.getDatabase(config.dbName).getUserCollection()

    @BeforeEach
    fun setUp() {
        config = ConfigStub()
        userRepository = UserRepositoryImpl(mongoClient, config)
    }


    @Test
    fun findByMcId() {
        userCollection.insertOne(user)

        assertEquals(user, userRepository.findUserByMcId("mc_id"))
    }

    @Test
    fun findByDiscordId() {
        userCollection.insertOne(user)

        assertEquals(user, userRepository.findUserByDiscordId("discord_id"))
    }

    @Test
    fun updateMcId() {
        userCollection.insertOne(user)

        val newMcId = "new_mc_id"
        val user = userRepository.updateMcId(user.id, newMcId)

        assertEquals(newMcId, user?.mcId)
    }
}