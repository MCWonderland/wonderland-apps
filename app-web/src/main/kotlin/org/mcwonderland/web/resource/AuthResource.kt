package org.mcwonderland.web.resource

import org.jetbrains.annotations.NotNull
import org.mcwonderland.domain.service.AuthService
import org.mcwonderland.web.request.LoginRequest
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("/auth")
class AuthResource(
    private val authService: AuthService
) {

    @POST
    @Path("/login")
    fun login(@NotNull request: LoginRequest): Response {
        authService.login(request.code)
        return Response.ok().build()
    }
}