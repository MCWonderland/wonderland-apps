package org.mcwonderland.access

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Updates.set
import com.mongodb.client.model.Updates.setOnInsert
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.model.Settings
import org.mcwonderland.domain.repository.RegistrationRepository

class RegistrationRepositoryImpl(
    private val mongoClient: MongoClient,
    private val config: Config
) : RegistrationRepository {

    private val database
        get() = mongoClient.getDatabase(config.dbName)

    private val regCol
        get() = database.getRegistrationCollection()

    private val settingsCol
        get() = database.getSettingsCollection()

    override fun isRegistered(userId: String): Boolean {
        return regCol
            .find(eq("_id", userId))
            .first()?.registered ?: false
    }

    override fun addRegistration(userId: String) {
        updateRegistrationState(userId, true)
    }

    override fun toggleRegistration(userId: String): Boolean {
        val newState = !isRegistered(userId)
        updateRegistrationState(userId, newState)
        return newState
    }

    override fun listRegistrations(): Collection<String> {
        return regCol.find(
            eq(RegistrationContext::registered.name, true)
        ).map { it.id }.toList()
    }

    override fun isAllowRegistrations(): Boolean {
        return settingsCol.findOneAndUpdate(
            eq("_id", config.settingsMongoId),
            setOnInsert(Settings::allowRegistrations.name, true),
            upsertAndReturn()
        )!!.allowRegistrations

    }

    override fun setAllowRegistrations(state: Boolean): Boolean {
        return settingsCol.findOneAndUpdate(
            eq("_id", config.settingsMongoId),
            set(Settings::allowRegistrations.name, state),
            upsertAndReturn()
        )!!.allowRegistrations
    }

    private fun updateRegistrationState(userId: String, b: Boolean) {
        regCol.findOneAndUpdate(
            eq("_id", userId),
            set(RegistrationContext::registered.name, b),
            upsertAndReturn()
        )
    }

    private fun upsertAndReturn(): FindOneAndUpdateOptions {
        return FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER)
    }
}