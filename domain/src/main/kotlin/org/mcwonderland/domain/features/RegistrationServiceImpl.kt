package org.mcwonderland.domain.features

import org.mcwonderland.domain.exceptions.NotAllowRegistrationsException
import org.mcwonderland.domain.exceptions.RequireLinkedAccountException
import org.mcwonderland.domain.model.Settings
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.RegistrationRepository
import org.mcwonderland.domain.repository.SettingsRepository
import org.mcwonderland.domain.repository.UserRepository

class RegistrationServiceImpl(
    private val accountLinker: AccountLinker,
    private val registrationRepository: RegistrationRepository,
    private val settingsRepository: SettingsRepository,
    private val userRepository: UserRepository
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

    override fun clearRegistrations(executor: User) {
        executor.checkAdminPermission()
        registrationRepository.clearRegistrations()
    }

    override fun toggleAllowRegistrations(user: User): Boolean {
        user.checkAdminPermission()
        val current = settingsRepository.isAllowRegistrations()

        return settingsRepository.setAllowRegistrations(!current)
    }

}