package org.mcwonderland.discord

import com.google.inject.Guice
import com.google.inject.Injector
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.mcwonderland.discord.commands.HelpHandleImpl
import org.mcwonderland.discord.config.Config
import org.mcwonderland.discord.listener.CommandListener
import org.mcwonderland.discord.module.BotActivity
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.CommandProcessorImpl
import org.mcwonderland.domain.commands.*
import org.mcwonderland.domain.module.ProjectVersion
import org.mcwonderland.domain.repository.UserRepository
import org.shanerx.mojang.Mojang

fun main() {
    val injector: Injector = Guice.createInjector(
        AppModule(mojangApi = Mojang().connect()),
        DatabaseModule(),
        CommandModule(),
        ServiceModule()
    )

    val config = injector.getInstance(Config::class.java)

    val jda = JDABuilder
        .createDefault(config.botToken)
        .enableIntents(listOf(GatewayIntent.MESSAGE_CONTENT))
        .build()
        .awaitReady()

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
        injector.getInstance(CommandToggleReg::class.java),
        injector.getInstance(CommandRemoveReg::class.java),
    )

    commands.add(
        CommandHelp(
            "help",
            commands,
            HelpHandleImpl(config.commandPrefix) as CommandHelpHandle<CommandContext>
        )
    )

    jda.addEventListener(
        CommandListener(
            CommandProcessorImpl(commands),
            config.commandPrefix,
            injector.getInstance(UserRepository::class.java),
        ),
    )

    BotActivity(jda).setActivity("版本 v${ProjectVersion(object {}::class.java.classLoader).get()}")
}