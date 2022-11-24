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

    @BeforeEach
    fun setUp() {
        config = ConfigStub()
        userRepository = UserRepositoryImpl(mongoClient, config)
    }


    @Test
    fun findByMcId() {
        val user = User(id = "123", mcId = "mc_id")

        mongoClient.getDatabase(config.dbName).getUserCollection().insertOne(user)

        assertEquals(user, userRepository.findUserByMcId("mc_id"))
    }
}