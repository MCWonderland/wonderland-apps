package org.mcwonderland.domain.repository

interface RegistrationRepository {

    fun isRegistered(userId: String): Boolean

    fun addRegistration(userId: String)

    fun toggleRegistration(userId: String): Boolean
    fun listRegistrations(): Collection<String>
    fun isAllowRegistrations(): Boolean
    fun setAllowRegistrations(state: Boolean): Boolean

}
