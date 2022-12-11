package org.mcwonderland.domain.service

import org.mcwonderland.domain.model.DiscordUser

interface DiscordAuthApi {

    fun findUserByCode(code: String): DiscordUser

}