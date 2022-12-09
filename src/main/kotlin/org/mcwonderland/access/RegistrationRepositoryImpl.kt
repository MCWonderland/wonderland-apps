package org.mcwonderland.access

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.set
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.repository.RegistrationRepository

class RegistrationRepositoryImpl(
    private val mongoClient: MongoClient,
    private val config: Config
) : RegistrationRepository {

    private val database
        get() = mongoClient.getDatabase(config.dbName)

    private val collection
        get() = database.getRegistrationCollection()

    override fun isRegistered(userId: String): Boolean {
        return collection
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
        return collection.find(
            eq(RegistrationContext::registered.name, true)
        ).map { it.id }.toList()
    }

    override fun clearRegistrations() {
        collection.drop()
    }

    private fun updateRegistrationState(userId: String, b: Boolean) {
        collection.findOneAndUpdate(
            eq("_id", userId),
            set(RegistrationContext::registered.name, b),
            upsertAndReturn()
        )
    }

}