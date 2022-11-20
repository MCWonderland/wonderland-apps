package org.mcwonderland.domain

import org.mcwonderland.domain.model.CommandSender
import org.mcwonderland.domain.model.User

object Dummies {

    fun createCommandSender() = CommandSender(
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