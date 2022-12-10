package org.mcwonderland.access

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.set
import com.mongodb.client.model.Updates.setOnInsert
import org.mcwonderland.domain.model.Settings
import org.mcwonderland.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val mongoClient: MongoClient,
    private val dbName: String,
    private val settingsId: String
) : SettingsRepository {

    private val collection
        get() = mongoClient.getDatabase(dbName).getSettingsCollection()

    override fun isAllowRegistrations(): Boolean {
        return collection.findOneAndUpdate(
            eq("_id", settingsId),
            setOnInsert(Settings::allowRegistrations.name, true),
            upsertAndReturn()
        )!!.allowRegistrations

    }

    override fun setAllowRegistrations(state: Boolean): Boolean {
        return collection.findOneAndUpdate(
            eq("_id", settingsId),
            set(Settings::allowRegistrations.name, state),
            upsertAndReturn()
        )!!.allowRegistrations
    }

}