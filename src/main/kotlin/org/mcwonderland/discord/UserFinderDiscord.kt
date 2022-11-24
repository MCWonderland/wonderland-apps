package org.mcwonderland.discord

import org.mcwonderland.domain.UserFinder
import org.mcwonderland.domain.model.User

class UserFinderDiscord : UserFinder {

    override fun find(platformId: String): User? {
        TODO("Not yet implemented")
    }

    override fun findOrCreate(platformId: String): User {
        TODO("Not yet implemented")
    }

}