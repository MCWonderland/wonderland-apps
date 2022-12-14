package org.mcwonderland.access

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.repository.RegistrationRepository
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class RegistrationRepositoryImplTest : MongoDBTest() {

    private lateinit var repo: RegistrationRepository

    private val collection: MongoCollection<RegistrationContext>
        get() = getDB().getRegistrationCollection()

    @BeforeEach
    fun setup() {
        repo = RegistrationRepositoryImpl(mongoClient, dbName)
    }

    @Nested
    inner class IsRegistered {

        @Test
        fun dataNotExist_shouldReturnFalse() {
            assertFalse { repo.isRegistered("123") }
        }

        @Test
        fun withData_shouldReturnState() {
            collection.insertOne(RegistrationContext("test", true))

            assertTrue { repo.isRegistered("test") }
        }

    }

    @Test
    fun addRegistration() {
        repo.addRegistration("test")

        assertTrue { collection.find(Filters.eq("_id", "test")).first()!!.registered }
    }

    @Test
    fun toggleRegistration() {
        repo.addRegistration("test")

        assertEquals(false, repo.toggleRegistration("test"))
        assertFalse { collection.find(Filters.eq("_id", "test")).first()!!.registered }
    }

    @Test
    fun listRegistrations() {
        collection.insertMany(
            listOf(
                RegistrationContext("test1", true),
                RegistrationContext("test2", false)
            )
        )

        assertEquals(listOf("test1"), repo.listRegistrations())
    }

    @Test
    fun clearRegistrations() {
        collection.insertMany(
            listOf(
                RegistrationContext("test1", true),
                RegistrationContext("test2", false)
            )
        )

        repo.clearRegistrations()

        assertEquals(emptyList(), collection.find().toList())
    }

    @Test
    fun removeRegistration() {
        collection.insertMany(
            listOf(
                RegistrationContext("test1", true),
                RegistrationContext("test2", true)
            )
        )

        repo.removeRegistration("test1")

        assertEquals(listOf("test2"), repo.listRegistrations())
    }

}