package org.mcwonderland.domain.model

data class DBTeam(
    var id: String = "",
    var members: List<String> = listOf()
)

fun Team.toDBTeam(): DBTeam {
    return DBTeam(id, members.map { it.id })
}

fun DBTeam.toTeam(userMapper: Collection<User>): Team {
    return Team(id, members.mapNotNull { memberId -> userMapper.find { memberId == it.id } })
}