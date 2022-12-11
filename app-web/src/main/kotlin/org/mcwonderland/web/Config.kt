package org.mcwonderland.web

import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
data class Config(
    @ConfigProperty(name = "quarkus.mongodb.db-name")
    val mongoDbName: String,
    @ConfigProperty(name = "discord.client-id")
    val clientId: String,
    @ConfigProperty(name = "discord.client-secret")
    val clientSecret: String,
    @ConfigProperty(name = "discord.redirect-uri")
    val redirectUri: String,
    @ConfigProperty(name = "jwt.cookie-key")
    val tokenCookieKey: String,
    @ConfigProperty(name = "website.domain")
    val websiteDomain: String
)
