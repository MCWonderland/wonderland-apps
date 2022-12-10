package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.features.AccountLinker
import org.mcwonderland.domain.model.User

class AccountLinkerFake : AccountLinker {

    private val linkMap = mutableMapOf<String, String>()

    override fun link(user: User, platformId: String): User {
        linkMap[user.id] = platformId
        return user
    }

    override fun isLinked(user: User): Boolean {
        return linkMap.containsKey(user.id)
    }

}