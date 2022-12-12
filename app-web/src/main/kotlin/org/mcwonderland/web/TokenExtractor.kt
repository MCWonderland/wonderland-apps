package org.mcwonderland.web

import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.service.UserTokenService
import javax.ws.rs.core.HttpHeaders

class TokenExtractor(
    private val config: Config,
    private val userTokenService: UserTokenService
) {

    fun extractUser(httpHeaders: HttpHeaders): User? {
        val token = getCookieToken(httpHeaders) ?: return null
        return userTokenService.decodeToken(token)
    }

    private fun getCookieToken(httpHeaders: HttpHeaders): String? {
        return httpHeaders.cookies[config.tokenCookieKey]?.value
    }
}
