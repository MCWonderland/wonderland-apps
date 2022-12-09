package org.mcwonderland.domain.features

import org.mcwonderland.domain.model.User

interface RegistrationService {
    fun toggleRegister(user: User): Boolean
    fun listRegistrations(executor: User): Collection<User>
    fun clearRegistrations(executor: User)
    fun toggleAllowRegistrations(user: User): Boolean
}
