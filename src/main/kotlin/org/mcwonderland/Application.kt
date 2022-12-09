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
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.UserFinder
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


    val commands = mutableListOf(
        injector.getInstance(CommandAddToTeam::class.java),
        injector.getInstance(CommandClearReg::class.java),
        injector.getInstance(CommandCreateTeam::class.java),
        injector.getInstance(CommandDeleteTeam::class.java),
        injector.getInstance(CommandLink::class.java),
        injector.getInstance(CommandListReg::class.java),
        injector.getInstance(CommandListTeams::class.java),
        injector.getInstance(CommandRegister::class.java),
        injector.getInstance(CommandRemoveTeam::class.java),
        injector.getInstance(CommandToggleReg::class.java)
    )

    commands.add(CommandHelp("help", commands, injector.getInstance(Messages::class.java)))

    jda.addEventListener(
        CommandListener(
            CommandProcessorImpl(commands, injector.getInstance(Messages::class.java)),
            injector.getInstance(Config::class.java),
            messenger,
            injector.getInstance(UserFinder::class.java)
        ),
    )
}