package org.mcwonderland.domain.testdoubles

import com.google.gson.Gson
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.service.UserTokenService

class UserTokenServiceFake : UserTokenService {

    override fun encodeToken(user: User): String {
        return Gson().toJson(user)
    }

    override fun decodeToken(token: String): User {
        return Gson().fromJson(token, User::class.java)
    }

}