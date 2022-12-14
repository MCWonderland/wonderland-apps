package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.model.DiscordProfile
import org.mcwonderland.domain.model.McProfile
import org.mcwonderland.domain.model.Team
import java.util.UUID

object Dummies {

    fun createUserEmpty(): UserStub {
        return UserStub()
    }

    fun createUserFullFilled(): UserStub {
        return UserStub(
            id = UUID.randomUUID().toString(),
            McProfile("mc_id", "mc_username"),
            DiscordProfile("discord_id", "discord_username"),
            isAdmin = false
        )
    }

    fun createTeam(): Team {
        return Team(
            members = listOf()
        )
    }

}