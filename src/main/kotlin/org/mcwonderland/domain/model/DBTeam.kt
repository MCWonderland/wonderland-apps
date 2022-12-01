package org.mcwonderland.domain.model

data class DBTeam(
    var members: List<String> = listOf()
)

fun Team.toDBTeam(): DBTeam {
    return DBTeam(members.map { it.id })
}

fun DBTeam.toTeam(userMapper: Collection<User>): Team {
    return Team(members.mapNotNull { memberId -> userMapper.find { memberId == it.id } })
}