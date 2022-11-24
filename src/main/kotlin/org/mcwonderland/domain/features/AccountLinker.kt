package org.mcwonderland.domain.features

import org.mcwonderland.domain.model.User

interface AccountLinker {
    fun link(user: User, platformId: String)
}
