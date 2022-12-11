package org.mcwonderland.access

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.model.DiscordProfile
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class UserRepositoryImplTest : MongoDBTest() {

    private lateinit var userRepository: UserRepositoryImpl

    private val user = Dummies.createUserFullFilled()

    private val userCollection
        get() = getDB().getUserCollection()

    @BeforeEach
    fun setUp() {
        userRepository = UserRepositoryImpl(mongoClient, dbName)
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

    @Test
    fun findUsers() {
        val users = listOf(user, user.copy(id = "456"))
        userCollection.insertMany(users)

        userRepository.findUsers(users.map { it.id }).let { result ->
            assertEquals(result.size, users.size)
            users.forEach {
                assertContains(result, it)
            }
        }
    }


    @Nested
    inner class FindUpdated {
        private val profile = DiscordProfile(user.discordId, "newName")

        @Test
        fun shouldCreateIfNotExist() {
            val user = userRepository.findUpdated(profile)

            assertEquals(profile.id, user.discordId)
            assertEquals(profile.username, user.discordUsername)
            assertEquals(user, userRepository.findUserByDiscordId(profile.id))
        }

        @Test
        fun shouldKeepUpdateDiscordName() {
            userCollection.insertOne(user)

            val user = userRepository.findUpdated(profile)

            assertEquals(profile.username, user.discordUsername)
        }
    }
}