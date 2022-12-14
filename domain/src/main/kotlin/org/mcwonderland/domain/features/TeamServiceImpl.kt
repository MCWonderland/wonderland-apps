package org.mcwonderland.domain.features

import org.mcwonderland.domain.exceptions.*
import org.mcwonderland.domain.model.*
import org.mcwonderland.domain.repository.TeamRepository
import org.mcwonderland.domain.repository.UserRepository

class TeamServiceImpl(
    private val userFinder: UserFinder,
    private val teamRepository: TeamRepository,
    private val userRepository: UserRepository,
    private val accountLinker: AccountLinker,
    private val idGenerator: IdGenerator
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


    override fun listTeams(): List<Team> {

        val dbTeams = teamRepository.findAll()
        val users = userRepository.findUsers(dbTeams.map { it.members }.flatten())

        return dbTeams.map { it.toTeam(users) }
    }

    override fun removeFromTeam(modification: UserModification): Team {
        modification.checkAdminPermission()

        val target = modification.findTargetForce(userFinder)
        val newTeam = teamRepository.removeUserFromTeam(target.id) ?: throw UserNotInTeamException(target)

        return newTeam.toTeam(userRepository.findUsers(newTeam.members))
    }

    override fun addUserToTeam(modification: UserModification, teamId: String): AddToTeamResult {
        modification.checkAdminPermission()

        val target = modification.findTargetForce(userFinder)
        checkAlreadyInTeam(target)

        val newTeam = teamRepository.addUserToTeam(target.id, teamId) ?: throw TeamNotFoundException(teamId)

        return AddToTeamResult(target, newTeam.toTeam(userRepository.findUsers(newTeam.members)))
    }

    override fun deleteTeam(sender: User, teamId: String) {
        sender.checkAdminPermission()
        teamRepository.deleteTeam(teamId) ?: throw TeamNotFoundException(teamId)
    }

    private fun checkEveryoneIsLinked(members: List<User>) {
        members.filter { !accountLinker.isLinked(it) }.let {
            if (it.isNotEmpty())
                throw UsersNotLinkedException(it)
        }
    }

    private fun checkAlreadyInTeam(target: User) {
        val team = teamRepository.findUsersTeam(target.id)

        if (team != null)
            throw UserAlreadyInTeamException(target)
    }

    private fun createTeamWith(members: List<User>): Team {
        val team = Team(id = idGenerator.generate(), members = members)
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
            throw UsersNotFoundException(nullUsers)

        return users
    }
}