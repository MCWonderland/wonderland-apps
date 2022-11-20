package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.config.Config

class ConfigStub : Config {

    override val commandPrefix: String
        get() = "!"
    override val dbName: String
        get() = "mcwonderland"

}