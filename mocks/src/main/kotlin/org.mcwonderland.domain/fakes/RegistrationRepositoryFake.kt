package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.repository.RegistrationRepository

class RegistrationRepositoryFake : RegistrationRepository {

    private val registeredUsers = mutableSetOf<String>()


    override fun isRegistered(userId: String): Boolean {
        return registeredUsers.contains(userId)
    }

    override fun addRegistration(userId: String) {
        registeredUsers.add(userId)
    }

    override fun removeRegistration(userId: String) {
        registeredUsers.remove(userId)
    }

    override fun toggleRegistration(userId: String): Boolean {
        if (isRegistered(userId)) {
            registeredUsers.remove(userId)
        } else {
            registeredUsers.add(userId)
        }

        return isRegistered(userId)
    }

    override fun listRegistrations(): Collection<String> {
        return this.registeredUsers
    }

    override fun clearRegistrations() {
        this.registeredUsers.clear()
    }

}
