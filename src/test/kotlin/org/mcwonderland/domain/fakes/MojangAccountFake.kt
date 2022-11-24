package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.MojangAccount

class MojangAccountFake : MojangAccount {
    private val accounts: MutableList<String> = mutableListOf()

    override fun isAccountExist(id: String): Boolean {
        return accounts.contains(id)
    }

    fun addAccount(id: String) {
        this.accounts.add(id)
    }
}