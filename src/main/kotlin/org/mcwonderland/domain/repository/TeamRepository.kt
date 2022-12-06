package org.mcwonderland.domain.repository

import org.mcwonderland.domain.model.DBTeam

interface TeamRepository {
    fun findUsersTeam(userId: String): DBTeam?
    fun insertTeam(team: DBTeam)
    fun findAll(): List<DBTeam>
    fun removeUserFromTeam(id: String): DBTeam?

}