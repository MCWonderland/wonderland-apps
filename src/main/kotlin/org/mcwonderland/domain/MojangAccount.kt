package org.mcwonderland.domain

import java.util.UUID

interface MojangAccount {
    fun getNameByUUID(uuid: String): String?
    fun getUUIDByName(ign: String): UUID?

}