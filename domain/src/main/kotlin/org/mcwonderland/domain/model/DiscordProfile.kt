package org.mcwonderland.domain.model

data class DiscordProfile(
    var id: String = "",
    var username: String = ""
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "id" to id,
            "username" to username
        )
    }
}