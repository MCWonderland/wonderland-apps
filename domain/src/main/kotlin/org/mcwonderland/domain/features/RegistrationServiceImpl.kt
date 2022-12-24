package org.mcwonderland.domain.features

import org.mcwonderland.domain.exceptions.NotAllowRegistrationsException
import org.mcwonderland.domain.exceptions.RequireLinkedAccountException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.RegistrationRepository
import org.mcwonderland.domain.repository.SettingsRepository
import org.mcwonderland.domain.repository.UserRepository

class RegistrationServiceImpl(
    private val accountLinker: AccountLinker,
    private val registrationRepository: RegistrationRepository,
    private val settingsRepository: SettingsRepository,
    private val userRepository: UserRepository,
    private val userFinder: UserFinder
) : RegistrationService {

    override fun toggleRegister(user: User): Boolean {
        if (!accountLinker.isLinked(user))
            throw RequireLinkedAccountException()

        if (!settingsRepository.isAllowRegistrations())
            throw NotAllowRegistrationsException()

        return registrationRepository.toggleRegistration(user.id)
    }

    override fun listRegistrations(): Collection<User> {
        return registrationRepository.listRegistrations().let { userRepository.findUsers(it) }
    }

    override fun clearRegistrations() {
        registrationRepository.clearRegistrations()
    }

    override fun toggleAllowRegistrations(): Boolean {
        val current = settingsRepository.isAllowRegistrations()

        return settingsRepository.setAllowRegistrations(!current)
    }

    override fun removeRegistration(userId: String): User {
        val user = userFinder.find(userId) ?: throw UserNotFoundException(userId)
        registrationRepository.removeRegistration(user.id)
        return user
    }

}