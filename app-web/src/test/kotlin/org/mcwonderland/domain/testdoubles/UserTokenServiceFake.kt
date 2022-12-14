package org.mcwonderland.domain.testdoubles

import com.google.gson.Gson
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.service.UserTokenService

class UserTokenServiceFake : UserTokenService {

    private val gson = Gson()

    override fun encodeToken(user: User): String {
        return gson.toJson(user)
    }

    override fun decodeToken(token: String): User {
        return gson.fromJson(token, User::class.java)
    }

}