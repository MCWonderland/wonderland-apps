package org.mcwonderland.domain

import org.mcwonderland.domain.model.User

interface UserFinder {

    fun find(platformId: String): User?
    fun findOrCreate(platformId: String): User

}