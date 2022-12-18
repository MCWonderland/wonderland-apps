package org.mcwonderland.discord

import com.google.inject.AbstractModule
import com.google.inject.Provides
import org.mcwonderland.discord.commands.*
import org.mcwonderland.discord.config.CommandLabels
import org.mcwonderland.discord.config.Messages
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.commands.*
import org.mcwonderland.domain.features.AccountLinker
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.features.TeamService

@Suppress("UNCHECKED_CAST")
class CommandModule() : AbstractModule() {

    interface CommandProviders {
        val commandLabels: CommandLabels
        val messages: Messages
    }

    @Provides
    fun commandProviders(
        commandLabels: CommandLabels,
        messages: Messages,
    ): CommandProviders {
        return object : CommandProviders {
            override val commandLabels: CommandLabels = commandLabels
            override val messages: Messages = messages
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
            handle = LinkHandleImpl(messages) as CommandLinkHandle<CommandContext>,
        )
    }

    @Provides
    fun commandCreateTeam(providers: CommandProviders, teamService: TeamService): CommandCreateTeam {
        return CommandCreateTeam(
            label = providers.commandLabels.createTeam,
            teamService = teamService,
            handle = TeamHandleImpl(providers.messages) as CommandCreateTeamHandle<CommandContext>
        )
    }

    @Provides
    fun commandRegister(providers: CommandProviders, registrationService: RegistrationService): CommandRegister {
        return CommandRegister(
            label = providers.commandLabels.register,
            registrationService = registrationService,
            handle = RegisterHandleImpl(providers.messages) as CommandRegisterHandle<CommandContext>
        )
    }

    @Provides
    fun commandRemoveTeam(providers: CommandProviders, teamService: TeamService): CommandRemoveTeam {
        return CommandRemoveTeam(
            label = providers.commandLabels.removeTeam,
            teamService = teamService,
            handle = CommandRemoveTeamHandleImpl(providers.messages) as CommandRemoveTeamHandle<CommandContext>
        )
    }

    @Provides
    fun commandListTeams(providers: CommandProviders, teamService: TeamService): CommandListTeams {
        return CommandListTeams(
            label = providers.commandLabels.listTeams,
            teamService = teamService,
            handle = ListTeamsHandleImpl(providers.messages) as CommandListTeamsHandle<CommandContext>
        )
    }

    @Provides
    fun commandListReg(providers: CommandProviders, registrationService: RegistrationService): CommandListReg {
        return CommandListReg(
            label = providers.commandLabels.listReg,
            registrationService = registrationService,
            handle = ListRegHandleImpl(providers.messages) as CommandListRegHandle<CommandContext>
        )
    }

    @Provides
    fun commandAddToTeam(
        providers: CommandProviders,
        teamService: TeamService
    ): CommandAddToTeam {
        return CommandAddToTeam(
            label = providers.commandLabels.addToTeam,
            teamService = teamService,
            handle = AddToTeamHandleImpl(providers.messages) as CommandAddToTeamHandle<CommandContext>
        )
    }

    @Provides
    fun commandDeleteTeam(providers: CommandProviders, teamService: TeamService): CommandDeleteTeam {
        return CommandDeleteTeam(
            label = providers.commandLabels.deleteTeam,
            teamService = teamService,
            handle = DeleteTeamHandleImpl(providers.messages) as CommandDeleteTeamHandle<CommandContext>
        )
    }

    @Provides
    fun commandClearReg(providers: CommandProviders, registrationService: RegistrationService): CommandClearReg {
        return CommandClearReg(
            label = providers.commandLabels.clearReg,
            registrationService = registrationService,
            handle = ClearRegHandleImpl(providers.messages) as CommandClearRegHandle<CommandContext>
        )
    }

    @Provides
    fun commandToggleReg(providers: CommandProviders, registrationService: RegistrationService): CommandToggleReg {
        return CommandToggleReg(
            label = providers.commandLabels.toggleReg,
            service = registrationService,
            handle = ToggleRegHandleImpl(providers.messages) as CommandToggleRegHandle<CommandContext>
        )
    }

    @Provides
    fun commandRemoveReg(providers: CommandProviders, registrationService: RegistrationService): CommandRemoveReg {
        return CommandRemoveReg(
            label = providers.commandLabels.removeReg,
            service = registrationService,
            handle = RemoveRegHandleImpl(providers.messages) as CommandRemoveRegHandle<CommandContext>
        )
    }

    @Provides
    fun commandClearTeams(providers: CommandProviders, teamService: TeamService): CommandClearTeams {
        return CommandClearTeams(
            label = providers.commandLabels.clearTeams,
            handle = ClearTeamsHandleImpl(providers.messages) as CommandClearTeamsHandle<CommandContext>,
            teamService = teamService
        )
    }

}