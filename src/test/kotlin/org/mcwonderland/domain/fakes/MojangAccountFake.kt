package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.MojangAccount
import java.util.*

class MojangAccountFake : MojangAccount {
    private val accounts = mutableSetOf<AccountData>()

    override fun getNameByUUID(uuid: String): String {
        return accounts.find { it.uuid.toString() == uuid }?.name ?: throw NoSuchElementException()
    }

    override fun getUUIDByName(ign: String): UUID? {
        return accounts.find { it.name == ign }?.uuid
    }

    fun addRandomAccount(): AccountData {
        val uuid = UUID.randomUUID()
        val account = AccountData(
            uuid = uuid,
            name = "name of $uuid"
        )
        accounts.add(account)

        return account
    }
}

data class AccountData(
    val uuid: UUID,
    val name: String
)