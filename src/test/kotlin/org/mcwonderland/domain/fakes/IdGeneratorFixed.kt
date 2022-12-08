package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.features.IdGenerator

class IdGeneratorFixed : IdGenerator {
    override fun generate(): String {
        return "fixed_id"
    }
}