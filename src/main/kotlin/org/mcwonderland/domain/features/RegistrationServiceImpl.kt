package org.mcwonderland.domain.features

import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.RequireLinkedAccountException
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.RegistrationRepository
import org.mcwonderland.domain.repository.UserRepository

class RegistrationServiceImpl(
    private val accountLinker: AccountLinker,
    private val registrationRepository: RegistrationRepository,
    private val userRepository: UserRepository
) : RegistrationService {

    override fun toggleRegister(user: User): Boolean {
        if (!accountLinker.isLinked(user))
            throw RequireLinkedAccountException()

        return registrationRepository.toggleRegistration(user.id)
    }

    override fun listRegistrations(executor: User): Collection<User> {
        if (!executor.isAdministrator())
            throw PermissionDeniedException()

        return registrationRepository.listRegistrations().let { userRepository.findUsers(it) }
    }

    override fun clearRegistrations(executor: User) {
        executor.checkAdminPermission()
        registrationRepository.clearRegistrations()
    }

}