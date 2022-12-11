package org.mcwonderland.access

import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.service.UserTokenService

class UserTokenServiceImpl : UserTokenService {
    override fun encodeToken(user: User): String {
        TODO("Not yet implemented")
    }

    override fun decodeToken(token: String): User {
        TODO("Not yet implemented")
    }
}