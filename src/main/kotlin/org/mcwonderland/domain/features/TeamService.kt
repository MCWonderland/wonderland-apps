package org.mcwonderland.domain.features

import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

interface TeamService {
    fun createTeam(executor: User, ids: List<String>): Team
    fun listTeams(): List<Team>
}