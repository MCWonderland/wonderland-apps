package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

object Dummies {

    fun createUserDefault(): User {
        return User(id = "123")
    }

    fun createUserFullFilled(): User {
        return User(
            id = "123",
            mcId = "123",
            discordId = "123",
            isAdmin = false
        )
    }

    fun createTeam(): Team {
        return Team(
            members = listOf()
        )
    }

}