package org.mcwonderland.domain.model

import org.mcwonderland.domain.exceptions.PermissionDeniedException

abstract class User {
    abstract var id: String
    abstract var mcProfile: McProfile
    abstract var discordProfile: DiscordProfile
    abstract var isAdmin: Boolean

    fun isAdministrator() = isAdmin

    fun checkAdminPermission() {
        if (!isAdmin)
            throw PermissionDeniedException()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (id != other.id) return false
        if (mcProfile != other.mcProfile) return false
        if (discordProfile != other.discordProfile) return false
        if (isAdmin != other.isAdmin) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + mcProfile.hashCode()
        result = 31 * result + discordProfile.hashCode()
        result = 31 * result + isAdmin.hashCode()
        return result
    }

}