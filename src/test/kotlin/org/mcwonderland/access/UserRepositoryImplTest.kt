package org.mcwonderland.access

import org.bson.Document
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class UserRepositoryImplTest : MongoDBTest() {

    private lateinit var userRepository: UserRepositoryImpl

    @BeforeEach
    fun setUp() {
        userRepository = UserRepositoryImpl()
    }


    @Nested
    inner class FindUserByMcId {

        @Test
        fun `should return null when user not found`() {

        }

    }

}