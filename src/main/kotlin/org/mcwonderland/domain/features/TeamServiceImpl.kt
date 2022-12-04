package org.mcwonderland.domain.features

import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.exceptions.*
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
    private val userRepository: UserRepository,
    private val accountLinker: AccountLinker
) : TeamService {

    override fun createTeam(executor: User, ids: List<String>): Team {
        if (!executor.isAdministrator())
            throw PermissionDeniedException()

        if (ids.isEmpty())
            throw MemberCantBeEmptyException()

        val idsWithNoDuplicate = ids.toSet().toList()
        val members = mapEveryIdToUserOrThrow(idsWithNoDuplicate)

        checkEveryMemberIsNotInTeam(members)
        checkEveryoneIsLinked(members)

        return createTeamWith(members)
    }


    private fun checkEveryoneIsLinked(members: List<User>) {
        members.filter { !accountLinker.isLinked(it) }.let {
            if (it.isNotEmpty())
                throw UsersNotLinkedException(it)
        }
    }

    override fun listTeams(executor: User): List<Team> {
        if (!executor.isAdministrator())
            throw RuntimeException(messages.noPermission())

        val dbTeams = teamRepository.findAll()
        val users = userRepository.findUsers(dbTeams.map { it.members }.flatten())

        return dbTeams.map { it.toTeam(users) }
    }

    override fun removeFromTeam(executor: User, targetId: String): Team {
        if (!executor.isAdministrator())
            throw RuntimeException(messages.noPermission())

        val target = userFinder.find(targetId) ?: throw RuntimeException(messages.userNotFound(targetId))
        val newTeam =
            teamRepository.removeUserFromTeam(target.id) ?: throw RuntimeException(messages.userNotInTeam(target))

        return newTeam.toTeam(userRepository.findUsers(newTeam.members))
    }

    private fun createTeamWith(members: List<User>): Team {
        val team = Team(members)
        teamRepository.insertTeam(team.toDBTeam())

        return team
    }

    private fun checkEveryMemberIsNotInTeam(members: List<User>) {
        val membersHasTeam = members.filter { teamRepository.findUsersTeam(it.id) != null }

        if (membersHasTeam.isNotEmpty())
            throw UsersAlreadyInTeamException(membersHasTeam)
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
            throw UserNotFoundException(nullUsers)

        return users
    }
}