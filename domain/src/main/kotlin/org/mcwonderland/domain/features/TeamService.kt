package org.mcwonderland.domain.features

import org.mcwonderland.domain.model.AddToTeamResult
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.model.UserModification

interface TeamService {
    fun createTeam(executor: User, ids: List<String>): Team
    fun listTeams(): List<Team>
    fun removeFromTeam(modification: UserModification): Team
    fun addUserToTeam(modification: UserModification, teamId: String): AddToTeamResult
    fun deleteTeam(sender: User, teamId: String)
}