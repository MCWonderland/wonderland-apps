package org.mcwonderland.minecraft

import org.mcwonderland.domain.MojangAccount
import org.shanerx.mojang.Mojang

class MojangAccountImpl(private val mojang: Mojang) : MojangAccount {

    override fun isAccountExist(id: String): Boolean {
        return try {
            mojang.getPlayerProfile(id) != null
        } catch (e: Exception) {
            false
        }
    }

}