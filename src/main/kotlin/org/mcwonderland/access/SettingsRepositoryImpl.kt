package org.mcwonderland.access

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.set
import com.mongodb.client.model.Updates.setOnInsert
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.model.Settings
import org.mcwonderland.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val mongoClient: MongoClient,
    private val config: Config
) : SettingsRepository {

    private val collection
        get() = mongoClient.getDatabase(config.dbName).getSettingsCollection()

    override fun isAllowRegistrations(): Boolean {
        return collection.findOneAndUpdate(
            eq("_id", config.settingsMongoId),
            setOnInsert(Settings::allowRegistrations.name, true),
            upsertAndReturn()
        )!!.allowRegistrations

    }

    override fun setAllowRegistrations(state: Boolean): Boolean {
        return collection.findOneAndUpdate(
            eq("_id", config.settingsMongoId),
            set(Settings::allowRegistrations.name, state),
            upsertAndReturn()
        )!!.allowRegistrations
    }

}