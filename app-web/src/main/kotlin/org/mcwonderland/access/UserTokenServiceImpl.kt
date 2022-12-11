package org.mcwonderland.access

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.service.UserTokenService

class UserTokenServiceImpl(private val jwtSettings: JwtConfig) : UserTokenService {


    override fun encodeToken(user: User): String {
        return JWT.create()
            .withClaim("id", user.id)
            .withClaim("discordId", user.discordId)
            .withClaim("discordUsername", user.discordUsername)
            .withClaim("mcId", user.mcId)
            .withClaim("isAdmin", user.isAdmin)
            .withIssuer(jwtSettings.issuer)
            .sign(jwtSettings.algorithm)
    }

    override fun decodeToken(token: String): User? {
        return try {
            val jwt = JWT.require(jwtSettings.algorithm).withIssuer(jwtSettings.issuer).build().verify(token)

            User(
                id = jwt.getClaim("id").asString(),
                discordId = jwt.getClaim("discordId").asString(),
                discordUsername = jwt.getClaim("discordUsername").asString(),
                mcId = jwt.getClaim("mcId").asString(),
                isAdmin = jwt.getClaim("isAdmin").asBoolean()
            )
        } catch (e: Exception) {
            null
        }
    }
}