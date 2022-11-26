package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.User

class UserFinderStub(private val user: User) : UserFinder {
    override fun find(platformId: String): User? {
        TODO("Not yet implemented")
    }

    override fun findOrCreate(platformId: String): User {
        return user
    }

}