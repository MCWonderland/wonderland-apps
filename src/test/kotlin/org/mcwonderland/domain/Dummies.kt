package org.mcwonderland.domain

import org.mcwonderland.domain.model.CommandSender

object Dummies {

    fun createCommandSender() = CommandSender(
        id = "123"
    )

}