package org.mcwonderland.domain.model

data class McProfile(
    var uuid: String = "",
    var username: String = "",
) {
    fun hasUuid(): Boolean {
        return uuid.isNotEmpty()
    }

    fun toMap(): Map<String, String> {
        return mapOf(
            "uuid" to uuid,
            "username" to username
        )
    }
}