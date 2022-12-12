package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

object Dummies {

    fun createUserDefault(): User {
        return User(id = "123")
    }

    fun createUserFullFilled(): User {
        return User(
            id = "id",
            mcId = "mc_id",
            mcUsername = "mc_username",
            discordId = "discord_id",
            discordUsername = "discord_username",
            isAdmin = false
        )
    }

    fun createTeam(): Team {
        return Team(
            members = listOf()
        )
    }

}