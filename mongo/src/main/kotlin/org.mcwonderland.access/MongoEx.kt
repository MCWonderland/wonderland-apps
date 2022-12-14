package org.mcwonderland.access

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import org.mcwonderland.domain.model.DBTeam
import org.mcwonderland.domain.model.Settings
import org.mcwonderland.domain.model.User

fun MongoDatabase.getUserCollection(): MongoCollection<MongoUser> = getCollection("user", MongoUser::class.java)
fun MongoDatabase.getTeamCollection(): MongoCollection<DBTeam> = getCollection("team", DBTeam::class.java)

fun MongoDatabase.getRegistrationCollection(): MongoCollection<RegistrationContext> =
    getCollection("registration", RegistrationContext::class.java)

fun MongoDatabase.getSettingsCollection(): MongoCollection<Settings> =
    getCollection("settings", Settings::class.java)

internal fun upsertAndReturn(): FindOneAndUpdateOptions {
    return FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER)
}
