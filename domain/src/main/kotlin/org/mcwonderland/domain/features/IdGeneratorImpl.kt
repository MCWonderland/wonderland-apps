package org.mcwonderland.domain.features

import java.util.*

class IdGeneratorImpl : IdGenerator {

    override fun generate(): String {
        return UUID.randomUUID().toString()
    }

}
