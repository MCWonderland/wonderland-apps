package org.mcwonderland

import com.google.inject.Guice
import com.google.inject.Injector
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.mcwonderland.discord.listener.CommandListener
import org.mcwonderland.domain.command.CommandProcessorImpl
import org.mcwonderland.domain.command.impl.*
import org.mcwonderland.domain.config.CommandLabels
import org.mcwonderland.domain.config.Config


class AppConfig : Config {
    override val commandPrefix: String = "!"
    override val dbName: String = "mcwonderland"
    override val mongoConnection: String = "mongodb://localhost:27017"

    override val commandLabels: CommandLabels = object : CommandLabels {
        override val createTeam: String = "createteam"
        override val link: String = "link"
        override val register: String = "register"
        override val removeTeam: String = "removeteam"
        override val listTeams: String = "listteams"
    }
}

fun main() {
    val jda = JDABuilder
        .createDefault(System.getenv("DISCORD_BOT_TOKEN"))
        .enableIntents(listOf(GatewayIntent.MESSAGE_CONTENT))
        .build()
        .awaitReady()

    val injector: Injector = Guice.createInjector(
        AppModule(jda.getTextChannelById("1046025295578275850")!!),
        DatabaseModule(),
        CommandModule(),
        ServiceModule()
    )

    val commands = listOf(
        injector.getInstance(CommandCreateTeam::class.java),
        injector.getInstance(CommandLink::class.java),
        injector.getInstance(CommandListTeams::class.java),
        injector.getInstance(CommandRegister::class.java),
        injector.getInstance(CommandRemoveTeam::class.java)
    )

    jda.addEventListener(
        CommandListener(
            CommandProcessorImpl(commands),
            injector.getInstance(Config::class.java)
        ),
    )
}