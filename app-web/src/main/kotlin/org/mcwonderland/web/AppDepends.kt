package org.mcwonderland.web

import com.mongodb.client.MongoClient
import org.mcwonderland.access.TeamRepositoryImpl
import org.mcwonderland.access.UserRepositoryImpl
import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.features.*
import org.mcwonderland.domain.repository.TeamRepository
import org.mcwonderland.domain.repository.UserRepository
import org.mcwonderland.mojang.MojangAccountImpl
import org.shanerx.mojang.Mojang
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.context.Dependent

@Dependent
class AppDepends {
    private val mojangApi = Mojang().connect()

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
    fun accountLinker(mojangAccount: MojangAccount, userRepository: UserRepository): AccountLinker {
        return DiscordMcIgnAccountLinker(mojangAccount, userRepository)
    }

    @ApplicationScoped
    fun mojangAccount(): MojangAccount {
        return MojangAccountImpl(mojangApi)
    }


}