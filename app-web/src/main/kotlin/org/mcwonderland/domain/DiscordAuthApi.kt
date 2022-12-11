package org.mcwonderland.domain

import org.mcwonderland.domain.model.DiscordUser

interface DiscordAuthApi {

    fun findUserByCode(code: String): DiscordUser

}