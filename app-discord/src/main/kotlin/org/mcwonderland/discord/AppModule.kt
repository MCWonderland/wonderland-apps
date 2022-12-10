package org.mcwonderland.discord

import com.google.inject.AbstractModule
import com.google.inject.Provides
import net.dv8tion.jda.api.JDA
import org.mcwonderland.discord.impl.DiscordMcIgnAccountLinker
import org.mcwonderland.discord.impl.UserFinderDiscord
import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.config.MessagesImpl
import org.mcwonderland.domain.features.AccountLinker
import org.mcwonderland.domain.features.IdGenerator
import org.mcwonderland.domain.features.IdGeneratorImpl
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.repository.UserRepository
import org.mcwonderland.mojang.MojangAccountImpl
import org.shanerx.mojang.Mojang
import java.io.File
import java.util.*

class AppModule(
    private val jda: JDA,
    private val mojangApi: Mojang,
) : AbstractModule() {

    private val mojangAccount = MojangAccountImpl(mojangApi)

    @Provides
    fun mojangAccount(): MojangAccount {
        return mojangAccount
    }

    @Provides
    fun config(): Config {
        val file = File("config.properties")
        val property = Properties().apply { load(file.inputStream()) }
        return AppConfig(property)
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
    fun userFinder(userRepository: UserRepository): UserFinder {
        return UserFinderDiscord(userRepository)
    }

    @Provides
    fun accountLinker(mojangAccount: MojangAccount, userRepository: UserRepository): AccountLinker {
        return DiscordMcIgnAccountLinker(mojangAccount, userRepository)
    }

    @Provides
    fun idGenerator(): IdGenerator {
        return IdGeneratorImpl()
    }

}