package org.mcwonderland.access

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.model.Settings
import org.mcwonderland.domain.repository.SettingsRepository
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SettingsRepositoryImplTest : MongoDBTest() {

    private val settingsCol
        get() = getDB().getSettingsCollection()

    private lateinit var repository: SettingsRepository

    private val settingsId = "settingsId"

    @BeforeEach
    fun setUp() {
        repository = SettingsRepositoryImpl(mongoClient, dbName, settingsId)
    }

    @Nested
    inner class IsAllowRegistrations {

        @Test
        fun default_shouldBeTrue() {
            assertTrue { repository.isAllowRegistrations() }
        }

        @Test
        fun withData_shouldReturnState() {
            settingsCol.insertOne(Settings(id = settingsId, allowRegistrations = false))

            assertFalse { repository.isAllowRegistrations() }
        }

    }

    @Test
    fun setAllowRegistrations() {
        repository.setAllowRegistrations(false)

        assertFalse { settingsCol.find().first()!!.allowRegistrations }
    }
}