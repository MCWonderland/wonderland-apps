package org.mcwonderland.web.resource

import org.jetbrains.annotations.NotNull
import org.mcwonderland.domain.service.AuthService
import org.mcwonderland.domain.service.UserTokenService
import org.mcwonderland.web.Config
import org.mcwonderland.web.request.LoginRequest
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.NewCookie
import javax.ws.rs.core.Response

@Path("/auth")
class AuthResource(
    private val authService: AuthService,
    private val userTokenService: UserTokenService,
    private val config: Config
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    fun login(@NotNull request: LoginRequest): Response {
        val user = authService.login(request.code)
        val token = userTokenService.encodeToken(user)

        return Response.ok().header(
            "Set-Cookie",
            NewCookie(
                config.tokenCookieKey,
                token,
                "/",
                config.websiteDomain.replace("www", ""),
                null,
                -1,
                false,
                false,
            ).toString().plus(";SameSite=Strict")
        ).build()
    }
}