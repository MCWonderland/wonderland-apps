package org.mcwonderland.domain.testdoubles

import org.mcwonderland.web.Config

class ConfigStub : Config {
    override val mongoDbName: String = "test"
    override val clientId: String = "test"
    override val clientSecret: String = "test"
    override val redirectUri: String = "test"
    override val tokenCookieKey: String = "test"
    override val websiteDomain: String = "test"
}