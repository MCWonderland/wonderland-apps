package org.mcwonderland.web.oidc

import org.mcwonderland.domain.exceptions.InvalidOAuthException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class InvalidOAuthExceptionMapper : BadRequestMapper<InvalidOAuthException>("Invalid OAuth")

open class BadRequestMapper<T : Exception>(private val message: String) : ExceptionMapper<T> {

    override fun toResponse(exception: T): Response {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(mapOf("message" to message))
            .build()
    }
}