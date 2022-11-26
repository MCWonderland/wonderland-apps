package org.mcwonderland

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.mcwonderland.access.MongoClientFactory
import org.mcwonderland.access.TeamRepositoryImpl
import org.mcwonderland.access.UserRepositoryImpl
import org.mcwonderland.discord.DiscordMcAccountLinker
import org.mcwonderland.discord.MessengerDiscordGuild
import org.mcwonderland.discord.listener.CommandListener
import org.mcwonderland.domain.UserFinderByDiscordId
import org.mcwonderland.domain.command.CommandProcessorImpl
import org.mcwonderland.domain.command.impl.CommandCreateTeam
import org.mcwonderland.domain.command.impl.CommandLink
import org.mcwonderland.domain.command.impl.CommandListTeams
import org.mcwonderland.domain.command.impl.CommandRemoveTeam
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.config.MessagesImpl
import org.mcwonderland.domain.config.MessagesStub
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.features.TeamServiceImpl
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
    val mojangAccount = MojangAccountImpl(mojang = mojangApi)
    val mongoClient = MongoClientFactory.createClient("mongodb://localhost:27017")
    val config = AppConfig()
    val userRepository = UserRepositoryImpl(mongoClient, config)
    val teamRepository = TeamRepositoryImpl(mongoClient, config)

    val channel = jda.getGuildById("574167124579319809")!!.getTextChannelById("1046025295578275850")!!
    val messages = MessagesImpl(mojangAccount)

    val accountLinker = DiscordMcAccountLinker(
        mojangAccount = mojangAccount,
        userRepository = userRepository,
        messages = messages
    )
    val messenger = MessengerDiscordGuild(channel)
    val userFinder = UserFinderByDiscordId(userRepository)

    val teamService = TeamServiceImpl(
        messages = messages,
        userFinder = userFinder,
        teamRepository = teamRepository,
        userRepository = userRepository,
        accountLinker = accountLinker
    )

    jda.addEventListener(
        CommandListener(
            CommandProcessorImpl(
                listOf(
                    CommandLink(
                        label = "link",
                        accountLinker = accountLinker,
                        userFinder = userFinder,
                        messenger = messenger,
                        messages
                    ),
                    CommandCreateTeam(
                        label = "createteam",
                        userFinder = userFinder,
                        messenger = messenger,
                        teamService = teamService,
                        messages = messages
                    ),
                    CommandListTeams(
                        label = "listteams",
                        messenger = messenger,
                        teamService = teamService,
                        messages = messages
                    ),
                    CommandRemoveTeam(
                        label = "removeteam",
                        messenger = messenger,
                        teamService = teamService,
                        userFinder = userFinder,
                        messages = messages
                    )
                )
            ),
            config
        ),
    )

}