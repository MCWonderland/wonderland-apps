package org.mcwonderland.minecraft

import org.mcwonderland.domain.MojangAccount
import org.shanerx.mojang.Mojang
import org.shanerx.mojang.PlayerProfile
import java.util.*

class MojangAccountImpl(private val mojang: Mojang) : MojangAccount {

    private val cache = mutableMapOf<String, PlayerProfile>()
    override fun getNameByUUID(uuid: String): String? {
        val profile = cache[uuid] ?: getFromMojang(uuid)

        if (profile != null)
            cache[uuid] = profile

        return profile?.username
    }

    private fun getFromMojang(uuid: String): PlayerProfile? {
        return try {
            mojang.getPlayerProfile(uuid)
        } catch (e: Exception) {
            null
        }
    }

    override fun getUUIDByName(ign: String): UUID? {
        return try {
            mojang.getUUIDOfUsername(ign)?.let { parseUuidNoDashed(it) }
        } catch (e: Exception) {
            null
        }
    }

    private fun parseUuidNoDashed(uuidStr: String): UUID {
        return UUID.fromString(uuidStr.replace(Regex("(.{8})(.{4})(.{4})(.{4})(.{12})"), "$1-$2-$3-$4-$5"))
    }
}