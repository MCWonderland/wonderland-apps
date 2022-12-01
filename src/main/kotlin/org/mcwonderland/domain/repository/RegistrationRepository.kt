package org.mcwonderland.domain.repository

import org.mcwonderland.domain.model.User

interface RegistrationRepository {

    fun isRegistered(userId: String): Boolean

    fun addRegistration(userId: String)

    fun toggleRegistration(userId: String): Boolean
    fun listRegistrations(): Collection<String>

}
