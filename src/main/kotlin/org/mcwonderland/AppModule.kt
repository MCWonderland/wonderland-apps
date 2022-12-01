package org.mcwonderland

import com.google.inject.AbstractModule
import com.google.inject.Provides
import net.dv8tion.jda.api.JDA
import org.mcwonderland.discord.DiscordMcIgnAccountLinker
import org.mcwonderland.discord.MessengerDiscordGuild
import org.mcwonderland.domain.Messenger
import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.UserFinderByDiscordId
import org.mcwonderland.domain.config.CommandLabels
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.config.MessagesImpl
import org.mcwonderland.domain.features.*
import org.mcwonderland.domain.repository.UserRepository
import org.mcwonderland.minecraft.MojangAccountImpl
import org.shanerx.mojang.Mojang
import java.io.File
import java.util.Properties

class AppModule(
    private val jda: JDA,
    private val mojangApi: Mojang,
) : AbstractModule() {

    @Provides
    fun mojangAccount(): MojangAccount {
        return MojangAccountImpl(mojang = mojangApi)
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
    fun accountLinker(mojangAccount: MojangAccount, userRepository: UserRepository, messages: Messages): AccountLinker {
        return DiscordMcIgnAccountLinker(mojangAccount, userRepository, messages)
    }

    @Provides
    fun messenger(config: Config): Messenger {
        return MessengerDiscordGuild(jda.getTextChannelById(config.commandChannelId)!!)
    }

    @Provides
    fun userFinder(userRepository: UserRepository): UserFinder {
        return UserFinderByDiscordId(userRepository)
    }

}