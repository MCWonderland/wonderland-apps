package org.mcwonderland.domain.features

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.domain.exceptions.*
import org.mcwonderland.domain.fakes.*
import org.mcwonderland.domain.model.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class TeamServiceImplTest {

    private lateinit var teamService: TeamService

    private lateinit var userFinder: UserFinderFake
    private lateinit var teamRepository: TeamRepositoryFake
    private lateinit var userRepository: UserRepositoryFake
    private lateinit var accountLinker: AccountLinker
    private lateinit var idGenerator: IdGenerator

    private lateinit var user: UserStub

    @BeforeEach
    fun setUp() {
        user = Dummies.createUserFullFilled()
        userFinder = UserFinderFake()
        teamRepository = TeamRepositoryFake()
        userRepository = UserRepositoryFake()
        accountLinker = mockk(relaxed = true)
        idGenerator = IdGeneratorFixed()

        teamService = TeamServiceImpl(userFinder, teamRepository, userRepository, accountLinker, idGenerator)
    }

    @Nested
    inner class CreateTeam {

        private val member = Dummies.createUserFullFilled()

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

            assertThrows<UsersNotFoundException> {
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

        @Test
        fun shouldAssignRandomId() {
            val team = successCreateTeam(listOf(member))
            assertEquals(team.id, idGenerator.generate())
        }

        private fun assertTeamCreation(inputUsers: List<User>, expectTeamMembers: List<User>) {
            val team = successCreateTeam(inputUsers)

            assertEquals(expectTeamMembers, team.members)

            expectTeamMembers.forEach {
                assertEquals(teamRepository.findUsersTeam(it.id), team.toDBTeam())
            }
        }

        private fun successCreateTeam(users: List<User>): Team {
            gainAdminPerm()

            users.forEach {
                userFinder.add(it)
                every { accountLinker.isLinked(it) } returns true
            }

            return teamService.createTeam(user, users.map { it.id })
        }
    }


    @Nested
    inner class ListTeams {

        @Test
        fun shouldListTeams() {
            val member = Dummies.createUserFullFilled()
            val team = teamRepository.createTeamWithUsers(member).toTeam(listOf(member))
            userRepository.addUser(member)
            user.addAdminPerm()

            val teams = teamService.listTeams()

            assertEquals(listOf(team), teams)
        }

    }

    @Nested
    inner class RemoveFromTeam {

        private val targetId = "target_id"
        private val target = Dummies.createUserFullFilled().apply { id = targetId }

        @Test
        fun withoutPermission_shouldDenied() {
            assertThrows<PermissionDeniedException> { teamService.removeFromTeam(UserModification(user, targetId)) }
        }

        @Test
        fun targetNotExist_shouldCancel() {
            gainAdminPerm()

            assertThrows<UserNotFoundException> {
                removeFromTeam()
            }.also {
                assertEquals(targetId, it.id)
            }
        }

        @Test
        fun targetNotInTeam_shouldCancel() {
            gainAdminPerm()
            userFinder.add(target)

            assertThrows<UserNotInTeamException> {
                removeFromTeam()
            }.also {
                assertEquals(target, it.user)
            }
        }

        @Test
        fun removeFromTeamAndReturnNewValue() {
            val dbTeam = teamRepository.createTeamWithUsers(target)

            gainAdminPerm()
            userFinder.add(target)

            val newTeam = removeFromTeam()

            assertEquals(dbTeam.toTeam(emptyList()), newTeam)
        }


        private fun removeFromTeam(): Team {
            return teamService.removeFromTeam(UserModification(user, targetId))
        }
    }


    @Nested
    inner class AddUserToTeam {

        private val teamId = "team_id"
        private val target = Dummies.createUserFullFilled()

        @Test
        fun withoutPermission_shouldDenied() {
            assertThrows<PermissionDeniedException> { addToTeam() }
        }

        @Test
        fun targetNotExist_shouldCancel() {
            gainAdminPerm()

            assertThrows<UserNotFoundException> {
                addToTeam()
            }.also {
                assertEquals(target.id, it.id)
            }
        }

        @Test
        fun targetAlreadyInTeam_shouldCancel() {
            gainAdminPerm()
            userFinder.add(target)
            teamRepository.createTeamWithUsers(target)

            assertThrows<UserAlreadyInTeamException> {
                addToTeam()
            }.also {
                assertEquals(target, it.user)
            }
        }

        @Test
        fun teamNotExist_shouldCancel() {
            gainAdminPerm()
            userFinder.add(target)

            assertThrows<TeamNotFoundException> {
                addToTeam()
            }.also {
                assertEquals(teamId, it.teamId)
            }
        }


        @Test
        fun shouldAddUserToTeam() {
            gainAdminPerm()
            userFinder.add(target)

            val dbTeam = teamRepository.createEmptyTeam(teamId)
            val result = addToTeam()

            assertContains(teamRepository.findUsersTeam(target.id)!!.members, target.id)
            assertEquals(result.user, target)
            assertEquals(result.team, dbTeam.toTeam(listOf(target)))
        }


        private fun addToTeam(): AddToTeamResult {
            return teamService.addUserToTeam(UserModification(user, target.id), teamId)
        }

    }


    @Nested
    inner class DeleteTeam {

        @Test
        fun withoutPermission_shouldDenied() {
            assertThrows<PermissionDeniedException> { teamService.deleteTeam(user, "team_id") }
        }

        @Test
        fun teamNotExist_shouldCancel() {
            gainAdminPerm()

            assertThrows<TeamNotFoundException> {
                teamService.deleteTeam(user, "team_id")
            }.also {
                assertEquals("team_id", it.teamId)
            }
        }

        @Test
        fun shouldDeleteTeam() {
            gainAdminPerm()

            val team = teamRepository.createEmptyTeam("team_id")
            teamService.deleteTeam(user, team.id)

            assertTrue(teamRepository.findAll().isEmpty())
        }
    }

    @Nested
    inner class ClearTeams {

        @Test
        fun shouldClearTeamsInDB() {
            teamRepository.createEmptyTeam("team_id")

            val amount = teamService.clearTeams()

            assertTrue(teamRepository.findAll().isEmpty())
            assertEquals(1, amount)
        }

    }

    private fun gainAdminPerm() {
        user.addAdminPerm()
    }
}