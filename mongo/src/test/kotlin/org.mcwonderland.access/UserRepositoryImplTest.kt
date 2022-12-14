package org.mcwonderland.access

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.fakes.UserStub
import org.mcwonderland.domain.model.User
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
        userCollection.insertOne(user.toMongoUser())


        assertTrue(user.equals(userRepository.findUserByMcId("mc_id")))
    }

    @Test
    fun findByDiscordId() {
        insert(user)

        assertEquals(user, userRepository.findUserByDiscordId("discord_id"))
    }

    @Test
    fun updateMcId() {
        insert(user)

        val newMcId = "new_mc_id"
        val user = userRepository.updateMcId(user.id, newMcId)

        assertEquals(newMcId, user?.mcProfile?.uuid)
    }

    @Test
    fun findUsers() {
        val users = listOf(user, UserStub(id = "456"))

        users.forEach { insert(it) }

        userRepository.findUsers(users.map { it.id }).let { result ->
            assertEquals(result.size, users.size)
            users.forEach {
                assertContains(result, it)
            }
        }
    }


    @Nested
    inner class FindUpdated {
        private val profile = user.discordProfile.copy(username = "newName")

        @Test
        fun shouldCreateIfNotExist() {
            val user = userRepository.findUpdated(profile)

            assertEquals(profile, user.discordProfile)
            assertEquals(user, userRepository.findUserByDiscordId(profile.id))
        }

        @Test
        fun shouldKeepUpdateDiscordName() {
            insert(user)

            val user = userRepository.findUpdated(profile)

            assertEquals(profile.username, user.discordProfile.username)
        }
    }

    private fun insert(user: User) {
        userCollection.insertOne(user.toMongoUser())
    }
}