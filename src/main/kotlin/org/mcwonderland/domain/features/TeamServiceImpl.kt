package org.mcwonderland.domain.features

import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.exception.PermissionDeniedException
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.model.toDBTeam
import org.mcwonderland.domain.model.toTeam
import org.mcwonderland.domain.repository.TeamRepository
import org.mcwonderland.domain.repository.UserRepository

class TeamServiceImpl(
    private val messages: Messages,
    private val userFinder: UserFinder,
    private val teamRepository: TeamRepository,
    private val userRepository: UserRepository
) : TeamService {

    override fun createTeam(executor: User, ids: List<String>): Team {
        if (!executor.isAdmin)
            throw PermissionDeniedException()

        if (ids.isEmpty())
            throw RuntimeException(messages.membersCantBeEmpty())

        val members = mapEveryIdToUserOrThrow(ids)
        checkEveryMemberIsNotInTeam(members)

        return createTeamWith(members)
    }

    override fun listTeams(): List<Team> {
        val dbTeams = teamRepository.findAll()
        val users = userRepository.findUsers(dbTeams.map { it.members }.flatten())

        return dbTeams.map { it.toTeam(users) }
    }

    private fun createTeamWith(members: List<User>): Team {
        val team = Team(members)
        teamRepository.insertTeam(team.toDBTeam())

        return team
    }

    private fun checkEveryMemberIsNotInTeam(members: List<User>) {
        val membersHasTeam = members.filter { teamRepository.findUsersTeam(it.id) != null }

        if (membersHasTeam.isNotEmpty())
            throw RuntimeException(messages.membersAlreadyInTeam(membersHasTeam))
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