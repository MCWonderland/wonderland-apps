package org.mcwonderland.discord

import com.google.inject.AbstractModule
import com.google.inject.Provides
import org.mcwonderland.discord.commands.CommandLinkHandleImpl
import org.mcwonderland.discord.config.CommandLabels
import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.domain.commands.CommandLink
import org.mcwonderland.domain.features.AccountLinker

class CommandModule(private val commandHistory: CommandHistory) : AbstractModule() {

    interface CommandProviders {
        val commandLabels: CommandLabels
    }

    @Provides
    fun commandProviders(
        commandLabels: CommandLabels,
    ): CommandProviders {
        return object : CommandProviders {
            override val commandLabels: CommandLabels = commandLabels
        }
    }

    @Provides
    fun commandLink(
        providers: CommandProviders,
        accountLinker: AccountLinker,
        messages: Messages,
    ): CommandLink {
        return CommandLink(
            label = providers.commandLabels.link,
            accountLinker = accountLinker,
            handle = CommandLinkHandleImpl(messages, commandHistory)
        )
    }
//
//    @Provides
//    fun commandCreateTeam(providers: CommandProviders, teamService: TeamService): CommandCreateTeam {
//        return CommandCreateTeam(
//            messages = providers.messages,
//            label = providers.commandLabels.createTeam,
//            teamService = teamService
//        )
//    }
//
//    @Provides
//    fun commandRegister(providers: CommandProviders, registrationService: RegistrationService): CommandRegister {
//        return CommandRegister(
//            messages = providers.messages,
//            label = providers.commandLabels.register,
//            registrationService = registrationService
//        )
//    }
//
//    @Provides
//    fun commandRemoveTeam(providers: CommandProviders, teamService: TeamService): CommandRemoveTeam {
//        return CommandRemoveTeam(
//            messages = providers.messages,
//            label = providers.commandLabels.removeTeam,
//            teamService = teamService
//        )
//    }
//
//    @Provides
//    fun commandListTeams(providers: CommandProviders, teamService: TeamService): CommandListTeams {
//        return CommandListTeams(
//            messages = providers.messages,
//            label = providers.commandLabels.listTeams,
//            teamService = teamService,
//        )
//    }
//
//    @Provides
//    fun commandListReg(providers: CommandProviders, registrationService: RegistrationService): CommandListReg {
//        return CommandListReg(
//            messages = providers.messages,
//            label = providers.commandLabels.listReg,
//            registrationService = registrationService,
//        )
//    }
//
//    @Provides
//    fun commandAddToTeam(providers: CommandProviders, teamService: TeamService): CommandAddToTeam {
//        return CommandAddToTeam(
//            messages = providers.messages,
//            label = providers.commandLabels.addToTeam,
//            teamService = teamService,
//        )
//    }
//
//    @Provides
//    fun commandDeleteTeam(providers: CommandProviders, teamService: TeamService): CommandDeleteTeam {
//        return CommandDeleteTeam(
//            messages = providers.messages,
//            label = providers.commandLabels.deleteTeam,
//            teamService = teamService,
//        )
//    }
//
//    @Provides
//    fun commandClearReg(providers: CommandProviders, registrationService: RegistrationService): CommandClearReg {
//        return CommandClearReg(
//            messages = providers.messages,
//            label = providers.commandLabels.clearReg,
//            registrationService = registrationService,
//        )
//    }
//
//    @Provides
//    fun commandToggleReg(providers: CommandProviders, registrationService: RegistrationService): CommandToggleReg {
//        return CommandToggleReg(
//            messages = providers.messages,
//            label = providers.commandLabels.toggleReg,
//            service = registrationService,
//        )
//    }

}