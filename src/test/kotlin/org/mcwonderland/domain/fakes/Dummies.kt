package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.model.PlatformUser
import org.mcwonderland.domain.model.User

object Dummies {

    fun createCommandSender() = PlatformUser(
        id = "123"
    )

    fun createUserDefault(): User {
        return User(id = "123")
    }

    fun createUserFullFilled(): User {
        return User(
            id = "123",
            mcId = "123",
            discordId = "123"
        )
    }

}