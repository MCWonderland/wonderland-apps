package org.mcwonderland.web.logic

import org.mcwonderland.domain.service.AuthService
import org.mcwonderland.domain.service.UserTokenService
import org.mcwonderland.web.Config
import org.mcwonderland.web.request.LoginRequest
import javax.ws.rs.core.NewCookie

class LoginProcess(
    private val authService: AuthService,
    private val userTokenService: UserTokenService,
    private val config: Config
) {

    fun login(request: LoginRequest): NewCookie {
        val user = authService.login(request.code)
        val token = userTokenService.encodeToken(user)

        return NewCookie(
            config.tokenCookieKey,
            token,
            "/",
            config.websiteDomain.replace("www", ""),
            null,
            -1,
            false,
            false,
        )

    }

}