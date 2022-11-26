package org.mcwonderland.domain.features

import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.RegistrationRepository

class RegistrationServiceImpl(
    private val accountLinker: AccountLinker,
    private val messages: Messages,
    private val registrationRepository: RegistrationRepository
) : RegistrationService {

    override fun toggleRegister(user: User): Boolean {
        if (!accountLinker.isLinked(user))
            throw RuntimeException(messages.yourAccountNotLinked())

        return registrationRepository.toggleRegistration(user.id)
    }

}