package org.mcwonderland

import com.google.inject.AbstractModule
import com.google.inject.Provides
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.*
import org.mcwonderland.domain.repository.RegistrationRepository
import org.mcwonderland.domain.repository.TeamRepository
import org.mcwonderland.domain.repository.UserRepository

class ServiceModule : AbstractModule() {
    @Provides
    fun teamService(
        messages: Messages,
        userFinder: UserFinder,
        teamRepository: TeamRepository,
        userRepository: UserRepository,
        accountLinker: AccountLinker
    ): TeamService {
        return TeamServiceImpl(
            messages = messages,
            userFinder = userFinder,
            teamRepository = teamRepository,
            userRepository = userRepository,
            accountLinker = accountLinker
        )
    }

    @Provides
    fun registrationService(
        registrationRepository: RegistrationRepository,
        userRepository: UserRepository,
        accountLinker: AccountLinker,
    ): RegistrationService {
        return RegistrationServiceImpl(
            registrationRepository = registrationRepository,
            userRepository = userRepository,
            accountLinker = accountLinker,
        )
    }

}