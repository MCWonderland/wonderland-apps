package org.mcwonderland.domain.model

data class Team(
    val id: String,
    val members: List<User>
)
