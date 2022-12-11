package org.mcwonderland

import com.mongodb.client.MongoClient
import io.mokulu.discord.oauth.DiscordOAuth
import org.mcwonderland.access.*
import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.features.*
import org.mcwonderland.domain.repository.RegistrationRepository
import org.mcwonderland.domain.repository.TeamRepository
import org.mcwonderland.domain.repository.UserRepository
import org.mcwonderland.domain.service.AuthService
import org.mcwonderland.domain.DiscordAuthApi
import org.mcwonderland.domain.service.UserTokenService
import org.mcwonderland.mojang.MojangAccountImpl
import org.mcwonderland.web.Config
import org.shanerx.mojang.Mojang
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.context.Dependent

@Dependent
class AppDepends {
    private val mojangApi = Mojang().connect()

    @ApplicationScoped
    fun authService(discordAuthApi: DiscordAuthApi, userFinder: UserFinder): AuthService {
        return AuthService(discordAuthApi, userFinder)
    }

    @ApplicationScoped
    fun userTokenService(): UserTokenService {
        return UserTokenServiceImpl()
    }

    @ApplicationScoped
    fun discordAuthApi(config: Config, discordApiCreator: DiscordApiCreator): DiscordAuthApi {
        return DiscordAuthApiImpl(
            DiscordOAuth(
                config.clientId,
                config.clientSecret,
                config.redirectUri,
                emptyArray()
            ),
            discordApiCreator
        )
    }

    @ApplicationScoped
    fun discordApiCreator(): DiscordApiCreator {
        return DiscordApiCreator()
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