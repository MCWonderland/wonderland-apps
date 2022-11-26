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

    override fun toggleRegistration(userId: String): Boolean {
        if (isRegistered(userId)) {
            registeredUsers.remove(userId)
        } else {
            registeredUsers.add(userId)
        }

        return isRegistered(userId)
    }

}
