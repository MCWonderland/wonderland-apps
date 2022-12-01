package org.mcwonderland.access

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Updates
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.repository.RegistrationRepository

class RegistrationRepositoryImpl(
    private val mongoClient: MongoClient,
    private val config: Config
) : RegistrationRepository {

    private val collection = mongoClient
        .getDatabase(config.dbName)
        .getRegistrationCollection()

    override fun isRegistered(userId: String): Boolean {
        return collection
            .find(Filters.eq("_id", userId))
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
            Filters.eq(RegistrationContext::registered.name, true)
        ).map { it.id }.toList()
    }

    private fun updateRegistrationState(userId: String, b: Boolean) {
        collection.findOneAndUpdate(
            Filters.eq("_id", userId),
            Updates.set(RegistrationContext::registered.name, b),
            FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER)
        )
    }
}