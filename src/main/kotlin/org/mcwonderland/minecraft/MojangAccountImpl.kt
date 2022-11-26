package org.mcwonderland.minecraft

import org.mcwonderland.domain.MojangAccount
import org.shanerx.mojang.Mojang
import org.shanerx.mojang.PlayerProfile

class MojangAccountImpl(private val mojang: Mojang) : MojangAccount {

    private val cache = mutableMapOf<String, PlayerProfile>()

    override fun isAccountExist(id: String): Boolean {
        return try {
            mojang.getPlayerProfile(id) != null
        } catch (e: Exception) {
            false
        }
    }

    override fun getNameByUUID(mcId: String): String? {
        val profile = cache[mcId] ?: mojang.getPlayerProfile(mcId)

        if (profile != null)
            cache[mcId] = profile

        return profile?.username
    }

}