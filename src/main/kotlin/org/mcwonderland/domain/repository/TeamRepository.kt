package org.mcwonderland.domain.repository

import org.mcwonderland.domain.model.Team

interface TeamRepository {

    fun findUsersTeam(userId: String): Team?

}