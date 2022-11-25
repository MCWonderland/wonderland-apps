package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.TeamRepository
import java.util.UUID

class TeamRepositoryFake : TeamRepository {

    private val teams = mutableListOf<Team>()

    override fun findUsersTeam(userId: String): Team? {
        TODO("Not yet implemented")
    }

    fun createTeamWithUsers(vararg users: User) {
        val team = Team(UUID.randomUUID().toString(), users.toList())
        teams.add(team)
    }

}