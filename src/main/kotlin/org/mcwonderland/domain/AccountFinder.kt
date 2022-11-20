package org.mcwonderland.domain

interface AccountFinder {

    fun isAccountExist(id: String): Boolean

}