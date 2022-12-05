package org.mcwonderland.domain.features

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.assertRuntimeError
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.config.MessagesStub
import org.mcwonderland.domain.exceptions.*
import org.mcwonderland.domain.fakes.TeamRepositoryFake
import org.mcwonderland.domain.fakes.UserFinderFake
import org.mcwonderland.domain.fakes.UserRepositoryFake
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.model.toDBTeam
import org.mcwonderland.domain.model.toTeam
import kotlin.test.assertEquals

internal class TeamServiceImplTest {

    private lateinit var teamService: TeamService

    private lateinit var messages: Messages
    private lateinit var userFinder: UserFinderFake
    private lateinit var teamRepository: TeamRepositoryFake
    private lateinit var userRepository: UserRepositoryFake
    private lateinit var accountLinker: AccountLinker

    private lateinit var user: User

    @BeforeEach
    fun setup() {
        user = User()
        userFinder = UserFinderFake()
        teamRepository = TeamRepositoryFake()
        userRepository = UserRepositoryFake()
        messages = MessagesStub()
        accountLinker = mockk(relaxed = true)

        teamService = TeamServiceImpl(messages, userFinder, teamRepository, userRepository, accountLinker)
    }

    @Nested
    inner class CreateTeam {

        private val member = User(id = "member_id")

        @Test
        fun executorWithoutPerm_shouldDenied() {
            assertThrows<PermissionDeniedException> { teamService.createTeam(user, listOf()) }
        }

        @Test
        fun membersIsEmpty_shouldCancel() {
            gainAdminPerm()

            assertThrows<MemberCantBeEmptyException> { teamService.createTeam(user, emptyList()) }
        }

        @Test
        fun membersCouldNotFound_shouldCancel() {
            gainAdminPerm()

            val ids = listOf("1", "2")

            assertThrows<UserNotFoundException> {
                teamService.createTeam(user, ids)
            }.also {
                assertEquals(ids, it.ids)
            }
        }


        @Test
        fun membersAlreadyInTeam_shouldCancel() {
            gainAdminPerm()

            userFinder.add(member)
            teamRepository.createTeamWithUsers(member)

            assertThrows<UsersAlreadyInTeamException> {
                teamService.createTeam(user, listOf(member.id))
            }.also {
                assertEquals(listOf(member), it.users)
            }
        }


        @Test
        fun memberNotLinked_shouldCancel() {
            gainAdminPerm()

            userFinder.add(member)

            assertThrows<UsersNotLinkedException> {
                teamService.createTeam(user, listOf(member.id))
            }.also {
                assertEquals(listOf(member), it.users)
            }
        }

        @Test
        fun shouldCreateTeam() {
            assertTeamCreation(listOf(member), listOf(member))
        }

        @Test
        fun shouldRemoveDuplicateMembers() {
            assertTeamCreation(listOf(member, member), listOf(member))
        }

        private fun assertTeamCreation(inputUsers: List<User>, expectTeamMembers: List<User>) {
            gainAdminPerm()

            inputUsers.forEach {
                userFinder.add(it)
                every { accountLinker.isLinked(it) } returns true
            }

            val team = teamService.createTeam(user, inputUsers.map { it.id })

            assertEquals(expectTeamMembers, team.members)

            expectTeamMembers.forEach {
                assertEquals(teamRepository.findUsersTeam(it.id), team.toDBTeam())
            }
        }


    }


    @Nested
    inner class ListTeams {

        @Test
        fun executorWithoutPerm_shouldDenied() {
            assertThrows<PermissionDeniedException> { teamService.listTeams(user) }
        }

        @Test
        fun shouldListTeams() {
            val member = User(id = "member_id")
            val team = teamRepository.createTeamWithUsers(member).toTeam(listOf(member))
            userRepository.addUser(member)
            user.addAdminPerm()

            val teams = teamService.listTeams(user)

            assertEquals(listOf(team), teams)
        }

    }

    @Nested
    inner class RemoveFromTeam {

        private val targetId = "target_id"
        private val target = User(id = "target_id")

        @Test
        fun withoutPermission_shouldDenied() {
            assertRuntimeError(messages.noPermission()) {
                teamService.removeFromTeam(user, targetId)
            }
        }

        @Test
        fun targetNotExist_shouldCancel() {
            gainAdminPerm()

            assertRuntimeError(messages.userNotFound(targetId)) {
                teamService.removeFromTeam(user, targetId)
            }
        }

        @Test
        fun targetNotInTeam_shouldCancel() {
            gainAdminPerm()
            userFinder.add(target)

            assertRuntimeError(messages.userNotInTeam(target)) {
                teamService.removeFromTeam(user, target.id)
            }
        }

        @Test
        fun removeFromTeamAndReturnNewValue() {
            teamRepository.createTeamWithUsers(target)

            gainAdminPerm()
            userFinder.add(target)

            val newTeam = teamService.removeFromTeam(user, target.id)

            assertEquals(Team(), newTeam)
        }

    }


    private fun gainAdminPerm() {
        user.addAdminPerm()
    }
}