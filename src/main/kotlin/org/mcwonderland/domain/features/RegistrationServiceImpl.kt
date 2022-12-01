package org.mcwonderland.domain.features

import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.RegistrationRepository
import org.mcwonderland.domain.repository.UserRepository

class RegistrationServiceImpl(
    private val accountLinker: AccountLinker,
    private val messages: Messages,
    private val registrationRepository: RegistrationRepository,
    private val userRepository: UserRepository
) : RegistrationService {

    override fun toggleRegister(user: User): Boolean {
        if (!accountLinker.isLinked(user))
            throw RuntimeException(messages.yourAccountNotLinked())

        return registrationRepository.toggleRegistration(user.id)
    }

    override fun listRegistrations(executor: User): Collection<User> {
        if (!executor.isAdministrator())
            throw RuntimeException(messages.noPermission())

        return registrationRepository.listRegistrations().let { userRepository.findUsers(it) }
    }

}