package org.mcwonderland.domain.features

import org.mcwonderland.domain.model.User

interface RegistrationService {
    fun toggleRegister(user: User): Boolean
    fun listRegistrations(): Collection<User>
    fun clearRegistrations()
    fun toggleAllowRegistrations(): Boolean
    fun removeRegistration(userId: String): User
}
