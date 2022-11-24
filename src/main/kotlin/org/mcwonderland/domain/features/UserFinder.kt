package org.mcwonderland.domain.features

import org.mcwonderland.domain.model.User

interface UserFinder {

    fun findOrCreate(platformId: String): User

}