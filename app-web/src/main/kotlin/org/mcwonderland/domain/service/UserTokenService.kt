package org.mcwonderland.domain.service

import org.mcwonderland.domain.model.User

interface UserTokenService {
    fun encodeToken(user: User): String
    fun decodeToken(token: String): User?
}
