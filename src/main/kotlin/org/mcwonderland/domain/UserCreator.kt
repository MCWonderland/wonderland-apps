package org.mcwonderland.domain

import org.mcwonderland.domain.model.User
import java.util.UUID

class UserCreator {

    fun create(): User {
        return User(id = UUID.randomUUID().toString())
    }

}
