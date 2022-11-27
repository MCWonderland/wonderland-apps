package org.mcwonderland

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.mongodb.client.MongoClient
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import org.mcwonderland.access.MongoClientFactory
import org.mcwonderland.access.RegistrationRepositoryImpl
import org.mcwonderland.access.TeamRepositoryImpl
import org.mcwonderland.access.UserRepositoryImpl
import org.mcwonderland.discord.DiscordMcIgnAccountLinker
import org.mcwonderland.discord.MessengerDiscordGuild
import org.mcwonderland.domain.Messenger
import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.UserFinderByDiscordId
import org.mcwonderland.domain.command.impl.*
import org.mcwonderland.domain.config.CommandLabels
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.config.MessagesImpl
import org.mcwonderland.domain.features.*
import org.mcwonderland.domain.repository.RegistrationRepository
import org.mcwonderland.domain.repository.TeamRepository
import org.mcwonderland.domain.repository.UserRepository
import org.mcwonderland.minecraft.MojangAccountImpl
import org.shanerx.mojang.Mojang

class AppModule(private val discordChannel: TextChannel) : AbstractModule() {
    private val mojangApi = Mojang().connect()

    @Provides
    fun mojangAccount(): MojangAccount {
        return MojangAccountImpl(mojang = mojangApi)
    }

    @Provides
    fun mongoClient(config: Config): MongoClient {
        return MongoClientFactory.createClient(config.mongoConnection)
    }

    @Provides
    fun config(): Config {
        return AppConfig()
    }

    @Provides
    fun commandLabels(config: Config): CommandLabels {
        return config.commandLabels
    }

    @Provides
    fun messages(mojangAccount: MojangAccount): Messages {
        return MessagesImpl(mojangAccount)
    }

    @Provides
    fun userRepository(mongoClient: MongoClient, config: Config): UserRepository {
        return UserRepositoryImpl(mongoClient, config)
    }

    @Provides
    fun teamRepository(mongoClient: MongoClient, config: Config): TeamRepository {
        return TeamRepositoryImpl(mongoClient, config)
    }

    @Provides
    fun registrationRepository(mongoClient: MongoClient, config: Config): RegistrationRepository {
        return RegistrationRepositoryImpl(mongoClient, config)
    }

    @Provides
    fun accountLinker(mojangAccount: MojangAccount, userRepository: UserRepository, messages: Messages): AccountLinker {
        return DiscordMcIgnAccountLinker(mojangAccount, userRepository, messages)
    }

    @Provides
    fun messenger(): Messenger {
        return MessengerDiscordGuild(discordChannel)
    }

    @Provides
    fun userFinder(userRepository: UserRepository): UserFinder {
        return UserFinderByDiscordId(userRepository)
    }

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
        messages: Messages,
        userFinder: UserFinder,
        registrationRepository: RegistrationRepository,
        userRepository: UserRepository,
        accountLinker: AccountLinker
    ): RegistrationService {
        return RegistrationServiceImpl(
            messages = messages,
            registrationRepository = registrationRepository,
            accountLinker = accountLinker
        )
    }

    interface CommandProviders {
        val accountLinker: AccountLinker
        val userFinder: UserFinder
        val messenger: Messenger
        val messages: Messages
        val commandLabels: CommandLabels
    }

    @Provides
    fun commandProviders(
        accountLinker: AccountLinker,
        userFinder: UserFinder,
        messenger: Messenger,
        messages: Messages,
        commandLabels: CommandLabels,
        teamService: TeamService
    ): CommandProviders {
        return object : CommandProviders {
            override val accountLinker: AccountLinker = accountLinker
            override val userFinder: UserFinder = userFinder
            override val messenger: Messenger = messenger
            override val messages: Messages = messages
            override val commandLabels: CommandLabels = commandLabels
        }
    }

    @Provides
    fun commandLink(providers: CommandProviders): CommandLink {
        return CommandLink(
            messages = providers.messages,
            userFinder = providers.userFinder,
            accountLinker = providers.accountLinker,
            label = providers.commandLabels.link,
            messenger = providers.messenger
        )
    }

    @Provides
    fun commandCreateTeam(providers: CommandProviders, teamService: TeamService): CommandCreateTeam {
        return CommandCreateTeam(
            messages = providers.messages,
            userFinder = providers.userFinder,
            label = providers.commandLabels.createTeam,
            messenger = providers.messenger,
            teamService = teamService
        )
    }

    @Provides
    fun commandRegister(providers: CommandProviders, registrationService: RegistrationService): CommandRegister {
        return CommandRegister(
            messages = providers.messages,
            userFinder = providers.userFinder,
            label = providers.commandLabels.register,
            messenger = providers.messenger,
            registrationService = registrationService
        )
    }

    @Provides
    fun commandRemoveTeam(providers: CommandProviders, teamService: TeamService): CommandRemoveTeam {
        return CommandRemoveTeam(
            messages = providers.messages,
            userFinder = providers.userFinder,
            label = providers.commandLabels.removeTeam,
            messenger = providers.messenger,
            teamService = teamService
        )
    }

    @Provides
    fun commandListTeams(providers: CommandProviders, teamService: TeamService): CommandListTeams {
        return CommandListTeams(
            messages = providers.messages,
            label = providers.commandLabels.listTeams,
            messenger = providers.messenger,
            teamService = teamService
        )
    }
}