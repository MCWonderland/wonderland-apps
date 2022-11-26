package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.MojangAccount

class MojangAccountFake : MojangAccount {
    private val accounts: MutableList<String> = mutableListOf()

    override fun isAccountExist(id: String): Boolean {
        return accounts.contains(id)
    }

    override fun getNameByUUID(mcId: String): String {
        return "name_of_$mcId"
    }

    fun addAccount(id: String) {
        this.accounts.add(id)
    }
}