package org.mcwonderland.web.resource

import io.quarkus.security.UnauthorizedException
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.web.context.TokenUser
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Response


@Path("/registrations")
class RegistrationResource(
    private val tokenUser: TokenUser,
    private val registrationService: RegistrationService
) {

    @GET
    fun listRegistrations(): Response {
        val user = tokenUser.get() ?: throw UnauthorizedException()

        return Response.ok(mapOf("users" to registrationService.listRegistrations())).build()
    }

}