package org.mcwonderland

import com.mongodb.client.MongoClient
import org.mcwonderland.access.RegistrationRepositoryImpl
import org.mcwonderland.access.TeamRepositoryImpl
import org.mcwonderland.access.UserRepositoryImpl
import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.features.*
import org.mcwonderland.domain.repository.RegistrationRepository
import org.mcwonderland.domain.repository.TeamRepository
import org.mcwonderland.domain.repository.UserRepository
import org.mcwonderland.domain.service.AuthService
import org.mcwonderland.domain.service.DiscordOAuth
import org.mcwonderland.mojang.MojangAccountImpl
import org.mcwonderland.web.Config
import org.shanerx.mojang.Mojang
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.context.Dependent

@Dependent
class AppDepends {
    private val mojangApi = Mojang().connect()

    @ApplicationScoped
    fun authService(discordOAuth: DiscordOAuth, userFinder: UserFinder): AuthService {
        return AuthService(discordOAuth, userFinder)
    }

    @ApplicationScoped
    fun teamService(
        userFinder: UserFinder,
        teamRepository: TeamRepository,
        userRepository: UserRepository,
        accountLinker: AccountLinker,
    ): TeamService {
        return TeamServiceImpl(
            userFinder,
            teamRepository,
            userRepository,
            accountLinker,
            IdGeneratorImpl()
        )
    }

    @ApplicationScoped
    fun userFinder(userRepository: UserRepository): UserFinder {
        return UserFinderDiscord(userRepository)
    }

    @ApplicationScoped
    fun userRepository(mongoClient: MongoClient, config: Config): UserRepository {
        return UserRepositoryImpl(mongoClient, config.mongoDbName)
    }

    @ApplicationScoped
    fun teamRepository(mongoClient: MongoClient, config: Config): TeamRepository {
        return TeamRepositoryImpl(mongoClient, config.mongoDbName)
    }

    @ApplicationScoped
    fun registrationRepository(mongoClient: MongoClient, config: Config): RegistrationRepository {
        return RegistrationRepositoryImpl(mongoClient, config.mongoDbName)
    }

    @ApplicationScoped
    fun accountLinker(mojangAccount: MojangAccount, userRepository: UserRepository): AccountLinker {
        return DiscordMcIgnAccountLinker(mojangAccount, userRepository)
    }

    @ApplicationScoped
    fun mojangAccount(): MojangAccount {
        return MojangAccountImpl(mojangApi)
    }


}