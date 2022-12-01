package org.mcwonderland.domain.model

data class User(
    var id: String = "",
    var mcId: String = "",
    var discordId: String = "",
    private var isAdmin: Boolean = false
) {

    fun addAdminPerm() {
        isAdmin = true
    }

    fun isAdministrator() = isAdmin
}