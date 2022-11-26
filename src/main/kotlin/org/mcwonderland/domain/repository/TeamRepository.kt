package org.mcwonderland.domain.repository

import org.mcwonderland.domain.model.DBTeam
import org.mcwonderland.domain.model.Team

interface TeamRepository {
    fun findUsersTeam(userId: String): DBTeam?
    fun insertTeam(team: DBTeam)
    fun findAll(): List<DBTeam>
    fun removeUserFromTeam(id: String): DBTeam?

}