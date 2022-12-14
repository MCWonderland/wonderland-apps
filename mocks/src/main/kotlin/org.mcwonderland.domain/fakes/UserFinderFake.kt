package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.User

class UserFinderFake : UserFinder {
    private val users = mutableMapOf<String, User>()

    override fun find(platformId: String): User? {
        return users[platformId]
    }

    fun add(member: User) {
        users[member.id] = member
    }

}