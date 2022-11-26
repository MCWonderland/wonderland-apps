package org.mcwonderland.access

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.model.DBTeam
import org.mcwonderland.domain.repository.TeamRepository

class TeamRepositoryImpl(
    private val mongoClient: MongoClient,
    private val config: Config
) : TeamRepository {

    private val collection
        get() = mongoClient.getDatabase(config.dbName).getTeamCollection()

    override fun findUsersTeam(userId: String): DBTeam? {
        return collection
            .find(Filters.`in`(DBTeam::members.name, userId))
            .first()
    }

    override fun insertTeam(team: DBTeam) {
        collection.insertOne(team)
    }
}