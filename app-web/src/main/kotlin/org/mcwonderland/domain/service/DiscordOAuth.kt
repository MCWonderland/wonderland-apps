package org.mcwonderland.domain.service

import org.mcwonderland.domain.model.DiscordUser

interface DiscordOAuth {

    fun findUserByCode(code: String): DiscordUser

}