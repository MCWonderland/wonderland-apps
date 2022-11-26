package org.mcwonderland.domain

interface MojangAccount {
    fun isAccountExist(id: String): Boolean
    fun getNameByUUID(mcId: String): String?

}