package org.mcwonderland

import com.google.inject.Guice
import com.google.inject.Injector
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.mcwonderland.discord.MessengerImpl
import org.mcwonderland.discord.listener.CommandListener
import org.mcwonderland.domain.command.CommandProcessorImpl
import org.mcwonderland.domain.command.impl.*
import org.mcwonderland.domain.config.Config
import org.shanerx.mojang.Mojang


fun main() {
    val jda = JDABuilder
        .createDefault(System.getenv("DISCORD_BOT_TOKEN"))
        .enableIntents(listOf(GatewayIntent.MESSAGE_CONTENT))
        .build()
        .awaitReady()

    val injector: Injector = Guice.createInjector(
        AppModule(jda = jda, mojangApi = Mojang().connect()),
        DatabaseModule(),
        CommandModule(),
        ServiceModule()
    )

    val messenger = MessengerImpl()


    val commands = listOf(
        injector.getInstance(CommandCreateTeam::class.java),
        injector.getInstance(CommandLink::class.java),
        injector.getInstance(CommandListTeams::class.java),
        injector.getInstance(CommandRegister::class.java),
        injector.getInstance(CommandRemoveTeam::class.java),
        injector.getInstance(CommandListReg::class.java)
    )

    jda.addEventListener(
        CommandListener(
            CommandProcessorImpl(commands),
            injector.getInstance(Config::class.java),
            messenger
        ),
    )
}