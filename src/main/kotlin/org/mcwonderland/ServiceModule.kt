package org.mcwonderland

import com.google.inject.AbstractModule
import com.google.inject.Provides
import org.mcwonderland.domain.features.*
import org.mcwonderland.domain.repository.RegistrationRepository
import org.mcwonderland.domain.repository.TeamRepository
import org.mcwonderland.domain.repository.UserRepository

class ServiceModule : AbstractModule() {
    @Provides
    fun teamService(
        userFinder: UserFinder,
        teamRepository: TeamRepository,
        userRepository: UserRepository,
        accountLinker: AccountLinker,
        idGenerator: IdGenerator
    ): TeamService {
        return TeamServiceImpl(
            userFinder = userFinder,
            teamRepository = teamRepository,
            userRepository = userRepository,
            accountLinker = accountLinker,
            idGenerator = idGenerator
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