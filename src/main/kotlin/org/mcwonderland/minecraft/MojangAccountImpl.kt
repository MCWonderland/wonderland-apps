package org.mcwonderland.minecraft

import org.mcwonderland.domain.MojangAccount
import org.shanerx.mojang.Mojang
import org.shanerx.mojang.PlayerProfile
import java.util.*

class MojangAccountImpl(private val mojang: Mojang) : MojangAccount {

    private val cache = mutableMapOf<String, PlayerProfile>()
    override fun getNameByUUID(uuid: String): String? {
        val profile = cache[uuid] ?: mojang.getPlayerProfile(uuid)

        if (profile != null)
            cache[uuid] = profile

        return profile?.username
    }

    override fun getUUIDByName(ign: String): UUID? {
        return mojang.getUUIDOfUsername(ign)?.let { parseUuidNoDashed(it) }
    }

    private fun parseUuidNoDashed(uuidStr: String): UUID {
        return UUID.fromString(uuidStr.replace(Regex("(.{8})(.{4})(.{4})(.{4})(.{12})"), "$1-$2-$3-$4-$5"))
    }
}