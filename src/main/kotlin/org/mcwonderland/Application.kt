package org.mcwonderland

import net.dv8tion.jda.api.JDABuilder
import org.mcwonderland.access.MongoClientFactory
import org.mcwonderland.access.UserRepositoryImpl
import org.mcwonderland.discord.DiscordMcAccountLinker
import org.mcwonderland.discord.MessageSenderDiscord
import org.mcwonderland.discord.UserFinderDiscord
import org.mcwonderland.discord.listener.CommandListener
import org.mcwonderland.domain.command.CommandProcessorImpl
import org.mcwonderland.domain.command.impl.CommandLink
import org.mcwonderland.domain.config.Config
import org.mcwonderland.minecraft.MojangAccountImpl
import org.shanerx.mojang.Mojang

class AppConfig : Config {
    override val commandPrefix: String = "!"
    override val dbName: String = "mcwonderland"
}

fun main() {
    val jda = JDABuilder.createDefault("token").build()
    val mojangApi = Mojang().connect()
    val mongoClient = MongoClientFactory.createClient("mongodb://localhost:27017")
    val config = AppConfig()

    jda.addEventListener(
        CommandListener(
            CommandProcessorImpl(
                listOf(
                    CommandLink(
                        label = "link",
                        accountLinker = DiscordMcAccountLinker(
                            mojangAccount = MojangAccountImpl(mojang = mojangApi),
                            userRepository = UserRepositoryImpl(mongoClient, config)
                        ),
                        userFinder = UserFinderDiscord(),
                        messageSender = MessageSenderDiscord()
                    )
                )
            ),
            config
        ),
    )
}