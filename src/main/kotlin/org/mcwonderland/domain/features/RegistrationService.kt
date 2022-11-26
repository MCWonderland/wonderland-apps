package org.mcwonderland.domain.features

import org.mcwonderland.domain.model.User

interface RegistrationService {
    fun register(user: User)
}
