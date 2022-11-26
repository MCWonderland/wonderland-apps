package org.mcwonderland.domain.model

data class DBTeam(
    val members: List<String>
)

fun Team.toDBTeam(): DBTeam {
    return DBTeam(members.map { it.id })
}

fun DBTeam.toTeam(): Team {
    return Team(members.map { User(it) })
}