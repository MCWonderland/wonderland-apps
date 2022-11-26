package org.mcwonderland.domain.config

import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

interface Messages {
    fun membersCantBeEmpty(): String
    fun membersCouldNotFound(ids: List<String>): String
    fun membersAlreadyInTeam(ids: List<User>): String
    fun accountAlreadyLinked(): String
    fun accountNotFound(): String
    fun targetAccountAlreadyLink(): String
    fun invalidArg(argName: String): String
    fun teamCreated(team: Team): String
    fun teamList(teams: List<Team>): String
    fun noPermission(): String
    fun userNotFound(targetId: String): String
    fun userNotInTeam(target: User): String
    fun userRemovedFromTeam(expectTeam: Team): String
    fun membersNotLinked(listOf: List<User>): String
    fun yourAccountNotLinked(): String
    fun linked(foundedUser: User): String
    fun registered(): String
    fun unRegistered(): String
}

