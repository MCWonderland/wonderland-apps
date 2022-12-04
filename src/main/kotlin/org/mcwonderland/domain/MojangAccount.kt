package org.mcwonderland.domain

import java.util.*

interface MojangAccount {
    fun getNameByUUID(uuid: String): String?
    fun getUUIDByName(ign: String): UUID?

}