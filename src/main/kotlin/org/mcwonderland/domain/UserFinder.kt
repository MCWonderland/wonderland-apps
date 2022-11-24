package org.mcwonderland.domain

import org.mcwonderland.domain.model.User

interface UserFinder {

    fun findOrCreate(platformId: String): User

}