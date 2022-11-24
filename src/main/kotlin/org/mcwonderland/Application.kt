package org.mcwonderland

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.mcwonderland.access.MongoClientFactory
import org.mcwonderland.access.UserRepositoryImpl
import org.mcwonderland.discord.DiscordMcAccountLinker
import org.mcwonderland.discord.MessageSenderDiscordGuild
import org.mcwonderland.discord.listener.CommandListener
import org.mcwonderland.domain.UserFinderByDiscordId
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
    val jda = JDABuilder
        .createDefault("MTA0NTM2Njk2NDM1NDMwMjA1NA.GQ5XSl.87Qk2W7YVyJXw-yoDxzrpvhBze3JMpmv_DKDJ0")
        .enableIntents(listOf(GatewayIntent.MESSAGE_CONTENT))
        .build()

    jda.awaitReady()

    val mojangApi = Mojang().connect()
    val mongoClient = MongoClientFactory.createClient("mongodb://localhost:27017")
    val config = AppConfig()
    val userRepository = UserRepositoryImpl(mongoClient, config)

    val channel = jda.getGuildById("574167124579319809")!!.getTextChannelById("1045370459149054012")!!

    jda.addEventListener(
        CommandListener(
            CommandProcessorImpl(
                listOf(
                    CommandLink(
                        label = "link",
                        accountLinker = DiscordMcAccountLinker(
                            mojangAccount = MojangAccountImpl(mojang = mojangApi),
                            userRepository = userRepository
                        ),
                        userFinder = UserFinderByDiscordId(userRepository),
                        messageSender = MessageSenderDiscordGuild(channel)
                    )
                )
            ),
            config
        ),
    )

}