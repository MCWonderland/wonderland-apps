package org.mcwonderland

import com.google.inject.AbstractModule
import com.google.inject.Provides
import org.mcwonderland.domain.command.impl.*
import org.mcwonderland.domain.config.CommandLabels
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.AccountLinker
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.features.UserFinder

class CommandModule : AbstractModule() {

    interface CommandProviders {
        val accountLinker: AccountLinker
        val userFinder: UserFinder
        val messages: Messages
        val commandLabels: CommandLabels
    }

    @Provides
    fun commandProviders(
        accountLinker: AccountLinker,
        userFinder: UserFinder,
        messages: Messages,
        commandLabels: CommandLabels,
        teamService: TeamService
    ): CommandProviders {
        return object : CommandProviders {
            override val accountLinker: AccountLinker = accountLinker
            override val userFinder: UserFinder = userFinder
            override val messages: Messages = messages
            override val commandLabels: CommandLabels = commandLabels
        }
    }

    @Provides
    fun commandLink(providers: CommandProviders): CommandLink {
        return CommandLink(
            messages = providers.messages,
            userFinder = providers.userFinder,
            accountLinker = providers.accountLinker,
            label = providers.commandLabels.link,
        )
    }

    @Provides
    fun commandCreateTeam(providers: CommandProviders, teamService: TeamService): CommandCreateTeam {
        return CommandCreateTeam(
            messages = providers.messages,
            userFinder = providers.userFinder,
            label = providers.commandLabels.createTeam,
            teamService = teamService
        )
    }

    @Provides
    fun commandRegister(providers: CommandProviders, registrationService: RegistrationService): CommandRegister {
        return CommandRegister(
            messages = providers.messages,
            userFinder = providers.userFinder,
            label = providers.commandLabels.register,
            registrationService = registrationService
        )
    }

    @Provides
    fun commandRemoveTeam(providers: CommandProviders, teamService: TeamService): CommandRemoveTeam {
        return CommandRemoveTeam(
            messages = providers.messages,
            userFinder = providers.userFinder,
            label = providers.commandLabels.removeTeam,
            teamService = teamService
        )
    }

    @Provides
    fun commandListTeams(providers: CommandProviders, teamService: TeamService): CommandListTeams {
        return CommandListTeams(
            messages = providers.messages,
            label = providers.commandLabels.listTeams,
            userFinder = providers.userFinder,
            teamService = teamService,
        )
    }

    @Provides
    fun commandListReg(providers: CommandProviders, registrationService: RegistrationService): CommandListReg {
        return CommandListReg(
            messages = providers.messages,
            label = providers.commandLabels.listReg,
            registrationService = registrationService,
            userFinder = providers.userFinder
        )
    }

}