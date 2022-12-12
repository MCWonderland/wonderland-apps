package org.mcwonderland.domain.model

import org.mcwonderland.domain.exceptions.PermissionDeniedException

data class User(
    var id: String = "",
    var mcId: String = "",
    var mcUsername: String = "",
    var discordId: String = "",
    var discordUsername: String = "",
    var isAdmin: Boolean = false
) {

    fun addAdminPerm() {
        isAdmin = true
    }

    fun isAdministrator() = isAdmin

    fun checkAdminPermission() {
        if (!isAdmin)
            throw PermissionDeniedException()
    }
}