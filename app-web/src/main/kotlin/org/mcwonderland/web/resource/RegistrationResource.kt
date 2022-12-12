package org.mcwonderland.web.resource

import io.quarkus.security.UnauthorizedException
import org.mcwonderland.web.TokenExtractor
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response

@Path("/registrations")
class RegistrationResource(
    private val tokenExtractor: TokenExtractor
) {

    @GET
    fun listRegistrations(@Context headers: HttpHeaders): Response {
        val user = tokenExtractor.extractUser(headers) ?: throw UnauthorizedException()
        return Response.ok().build()
    }

}