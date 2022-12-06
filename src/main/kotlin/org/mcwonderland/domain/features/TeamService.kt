package org.mcwonderland.domain.features

import org.mcwonderland.domain.model.AddToTeamResult
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

interface TeamService {
    fun createTeam(executor: User, ids: List<String>): Team
    fun listTeams(executor: User): List<Team>
    fun removeFromTeam(executor: User, targetId: String): Team
    fun addUsersToTeam(executor: User, targetId: String, teamId: String): AddToTeamResult
}