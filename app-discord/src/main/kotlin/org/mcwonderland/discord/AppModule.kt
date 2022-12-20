package org.mcwonderland.discord

import com.google.inject.AbstractModule
import com.google.inject.Provides
import net.dv8tion.jda.api.JDA
import org.mcwonderland.discord.config.CommandLabels
import org.mcwonderland.discord.config.Config
import org.mcwonderland.discord.config.Messages
import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.features.*
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
        return AppConfig(file)
    }

    @Provides
    fun commandLabels(config: Config): CommandLabels {
        return config.commandLabels
    }

    @Provides
    fun messages(mojangAccount: MojangAccount, config: Config): Messages {
        return Messages(mojangAccount, config)
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