package org.mcwonderland.web

import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
data class ConfigImpl(
    @ConfigProperty(name = "quarkus.mongodb.db-name")
    override val mongoDbName: String,
    @ConfigProperty(name = "discord.client-id")
    override val clientId: String,
    @ConfigProperty(name = "discord.client-secret")
    override val clientSecret: String,
    @ConfigProperty(name = "discord.redirect-uri")
    override val redirectUri: String,
    @ConfigProperty(name = "jwt.cookie-key")
    override val tokenCookieKey: String,
    @ConfigProperty(name = "jwt.secret")
    override val jwtSecret: String,
    @ConfigProperty(name = "jwt.issuer")
    override val jwtIssuer: String,
    @ConfigProperty(name = "website.domain")
    override val websiteDomain: String,
    @ConfigProperty(name = "settings.id")
    override val settingsId: String,
) : Config {
}
