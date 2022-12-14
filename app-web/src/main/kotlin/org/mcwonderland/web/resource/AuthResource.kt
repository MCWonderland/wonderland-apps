package org.mcwonderland.web.resource

import org.mcwonderland.web.logic.LoginProcess
import org.mcwonderland.web.request.LoginRequest
import javax.validation.constraints.NotNull
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/auth")
class AuthResource(
    private val loginProcess: LoginProcess
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    fun login(@NotNull request: LoginRequest): Response {
        val setCookieHeader = loginProcess.login(request).toString().plus(";SameSite=Strict")
        return Response.ok().header("Set-Cookie", setCookieHeader).build()
    }
}