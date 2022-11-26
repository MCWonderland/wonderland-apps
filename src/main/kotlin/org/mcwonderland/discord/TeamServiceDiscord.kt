package org.mcwonderland.discord

import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.exception.PermissionDeniedException
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.model.toDBTeam
import org.mcwonderland.domain.repository.TeamRepository

class TeamServiceDiscord(
    private val messages: Messages,
    private val userFinder: UserFinder,
    private val teamRepository: TeamRepository
) : TeamService {

    override fun createTeam(executor: User, ids: List<String>): Team {
        if (!executor.isAdmin)
            throw PermissionDeniedException()

        if (ids.isEmpty())
            throw RuntimeException(messages.membersCantBeEmpty())

        val members = mapEveryIdToUserOrThrow(ids)
        checkEveryMemberIsNotInTeam(members)

        val team = Team(members)
        teamRepository.insertTeam(team.toDBTeam())

        return team
    }

    private fun checkEveryMemberIsNotInTeam(members: List<User>) {
        val membersHasTeam = members.filter { teamRepository.findUsersTeam(it.id) != null }

        if (membersHasTeam.isNotEmpty())
            throw RuntimeException(messages.membersAlreadyInTeam(membersHasTeam.map { it.id }))
    }

    private fun mapEveryIdToUserOrThrow(ids: List<String>): List<User> {
        val nullUsers = mutableListOf<String>()

        val users = ids.mapNotNull {
            val found = userFinder.find(it)

            if (found == null)
                nullUsers.add(it)

            found
        }

        if (nullUsers.isNotEmpty())
            throw RuntimeException(messages.membersCouldNotFound(nullUsers))

        return users
    }
}