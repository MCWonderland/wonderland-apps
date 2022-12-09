package org.mcwonderland.access

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.model.Settings
import org.mcwonderland.domain.repository.RegistrationRepository
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class RegistrationRepositoryImplTest : MongoDBTest() {

    private lateinit var repo: RegistrationRepository

    private val database
        get() = mongoClient.getDatabase(config.dbName)

    private val registrationCol: MongoCollection<RegistrationContext>
        get() = database.getRegistrationCollection()

    private val settingsCol
        get() = database.getSettingsCollection()

    @BeforeEach
    fun setup() {
        repo = RegistrationRepositoryImpl(mongoClient, config)
    }

    @Nested
    inner class IsRegistered {

        @Test
        fun dataNotExist_shouldReturnFalse() {
            assertFalse { repo.isRegistered("123") }
        }

        @Test
        fun withData_shouldReturnState() {
            registrationCol.insertOne(RegistrationContext("test", true))

            assertTrue { repo.isRegistered("test") }
        }

    }

    @Test
    fun addRegistration() {
        repo.addRegistration("test")

        assertTrue { registrationCol.find(Filters.eq("_id", "test")).first()!!.registered }
    }

    @Test
    fun toggleRegistration() {
        repo.addRegistration("test")

        assertEquals(false, repo.toggleRegistration("test"))
        assertFalse { registrationCol.find(Filters.eq("_id", "test")).first()!!.registered }
    }

    @Test
    fun listRegistrations() {
        registrationCol.insertMany(
            listOf(
                RegistrationContext("test1", true),
                RegistrationContext("test2", false)
            )
        )

        assertEquals(listOf("test1"), repo.listRegistrations())
    }


    @Nested
    inner class IsAllowRegistrations {

        @Test
        fun default_shouldBeTrue() {
            assertTrue { repo.isAllowRegistrations() }
        }

        @Test
        fun withData_shouldReturnState() {
            settingsCol.insertOne(Settings(id = config.settingsMongoId, allowRegistrations = false))

            assertFalse { repo.isAllowRegistrations() }
        }

    }

    @Test
    fun setAllowRegistrations() {
        repo.setAllowRegistrations(false)

        assertFalse { settingsCol.find().first()!!.allowRegistrations }
    }
}