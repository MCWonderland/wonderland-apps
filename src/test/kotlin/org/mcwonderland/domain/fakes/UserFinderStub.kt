package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.User

class UserFinderStub(private val user: User) : UserFinder {

    override fun findOrCreate(platformId: String): User {
        return user
    }

}