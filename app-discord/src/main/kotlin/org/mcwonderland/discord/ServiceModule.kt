package org.mcwonderland.discord

import com.google.inject.AbstractModule
import com.google.inject.Provides
import org.mcwonderland.domain.features.*
import org.mcwonderland.domain.repository.RegistrationRepository
import org.mcwonderland.domain.repository.SettingsRepository
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
        settingsRepository: SettingsRepository,
        accountLinker: AccountLinker,
        userFinder: UserFinder
    ): RegistrationService {
        return RegistrationServiceImpl(
            registrationRepository = registrationRepository,
            userRepository = userRepository,
            settingsRepository = settingsRepository,
            accountLinker = accountLinker,
            userFinder = userFinder
        )
    }

}