package org.mcwonderland.web

interface Config {
    val mongoDbName: String
    val settingsId: String
    val clientId: String
    val clientSecret: String
    val redirectUri: String
    val tokenCookieKey: String
    val websiteDomain: String
    val jwtSecret: String
    val jwtIssuer: String
}