package org.mcwonderland.access

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Updates
import org.mcwonderland.domain.model.DBTeam
import org.mcwonderland.domain.repository.TeamRepository

class TeamRepositoryImpl(
    private val mongoClient: MongoClient,
    private val dbName: String,
) : TeamRepository {

    private val collection
        get() = mongoClient.getDatabase(dbName).getTeamCollection()

    override fun findUsersTeam(userId: String): DBTeam? {
        return collection
            .find(Filters.`in`(DBTeam::members.name, userId))
            .first()
    }

    override fun insertTeam(team: DBTeam) {
        collection.insertOne(team)
    }

    override fun findAll(): List<DBTeam> {
        return collection.find().toList()
    }

    override fun removeUserFromTeam(id: String): DBTeam? {
        return collection.findOneAndUpdate(
            Filters.`in`(DBTeam::members.name, id),
            Updates.pull(DBTeam::members.name, id),
            FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        )
    }

    override fun addUserToTeam(userId: String, teamId: String): DBTeam? {
        return collection.findOneAndUpdate(
            Filters.eq("_id", teamId),
            Updates.addToSet(DBTeam::members.name, userId),
            FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        )
    }

    override fun deleteTeam(teamId: String): DBTeam? {
        return collection.findOneAndDelete(Filters.eq("_id", teamId))
    }
}