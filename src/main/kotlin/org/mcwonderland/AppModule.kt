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

}