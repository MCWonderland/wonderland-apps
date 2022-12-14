package org.mcwonderland.discord

import com.google.inject.Guice
import com.google.inject.Injector
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.mcwonderland.discord.impl.MessengerImpl
import org.mcwonderland.discord.listener.CommandListener
import org.mcwonderland.domain.command.CommandProcessorImpl
import org.mcwonderland.domain.command.impl.*
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.repository.UserRepository
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
    val messages = injector.getInstance(Messages::class.java)
    val config = injector.getInstance(Config::class.java)


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

    commands.add(CommandHelp("help", commands, messages))

    jda.addEventListener(
        CommandListener(
            CommandProcessorImpl(commands, messages),
            config.commandPrefix,
            messenger,
            injector.getInstance(UserRepository::class.java)
        ),
    )
}