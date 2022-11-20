package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.model.User
import java.util.*

interface AccountLinker {
    fun link(user: User, platformId: String)
}
