package org.mcwonderland.access

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.mcwonderland.domain.model.DiscordProfile
import org.mcwonderland.domain.model.McProfile
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.service.UserTokenService

class UserTokenServiceImpl(private val jwtSettings: JwtConfig) : UserTokenService {


    override fun encodeToken(user: User): String {
        return JWT.create()
            .withClaim("id", user.id)
            .withClaim("discordId", user.discordProfile.id)
            .withClaim("discordUsername", user.discordProfile.username)
            .withClaim("mcId", user.mcProfile.uuid)
            .withClaim("mcUsername", user.mcProfile.username)
            .withClaim("isAdmin", user.isAdmin)
            .withIssuer(jwtSettings.issuer)
            .sign(jwtSettings.algorithm)
    }

    override fun decodeToken(token: String): User? {
        return try {
            val jwt = JWT.require(jwtSettings.algorithm).withIssuer(jwtSettings.issuer).build().verify(token)

            TokenUser(
                id = jwt.getClaim("id").asString(),
                discordId = jwt.getClaim("discordId").asString(),
                discordUsername = jwt.getClaim("discordUsername").asString(),
                mcUsername = jwt.getClaim("mcUsername").asString(),
                mcId = jwt.getClaim("mcId").asString(),
                isAdmin = jwt.getClaim("isAdmin").asBoolean()
            )
        } catch (e: Exception) {
            null
        }
    }

    class TokenUser(
        override var id: String,
        val discordId: String,
        val discordUsername: String,
        val mcId: String,
        val mcUsername: String,
        override var isAdmin: Boolean
    ) : User() {
        override var mcProfile: McProfile
            get() = McProfile(mcId, mcUsername)
            set(value) {}
        override var discordProfile: DiscordProfile
            get() = DiscordProfile(discordId, discordUsername)
            set(value) {}
    }
}