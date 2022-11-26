package org.mcwonderland.domain.model

data class DBTeam(
    var members: List<String> = listOf()
)

fun Team.toDBTeam(): DBTeam {
    return DBTeam(members.map { it.id })
}

fun DBTeam.toTeam(userMapper: List<User>): Team {
    return Team(members.map { memberId -> userMapper.first { memberId == it.id } })
}