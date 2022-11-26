package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.model.DBTeam
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.TeamRepository
import java.util.UUID

class TeamRepositoryFake : TeamRepository {

    private val teams = mutableListOf<DBTeam>()

    override fun findUsersTeam(userId: String): DBTeam? {
        return teams.find { it.members.contains(userId) }
    }

    override fun insertTeam(team: DBTeam) {
        teams.add(team)
    }

    override fun findAll(): List<DBTeam> {
        return teams.toList()
    }

    fun createTeamWithUsers(vararg users: User): DBTeam {
        val team = DBTeam(users.toList().map { it.id })
        teams.add(team)

        return team
    }

}