package org.mcwonderland.web.context

import io.quarkus.arc.InstanceHandle
import org.mcwonderland.domain.model.User
import org.mcwonderland.web.TokenExtractor
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.core.HttpHeaders

@ApplicationScoped
class TokenUser(
    private val tokenExtractor: TokenExtractor,
    private val headers: HttpHeaders
) : InstanceHandle<User?> {

    override fun get(): User? {
        return tokenExtractor.extractUser(headers)
    }

}