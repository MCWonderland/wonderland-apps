package org.mcwonderland.domain.features

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.assertError
import org.mcwonderland.assertRuntimeError
import org.mcwonderland.domain.config.Messages
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

    private lateinit var user: User

    @BeforeEach
    fun setup() {
        user = User()
        userFinder = UserFinderFake()
        teamRepository = TeamRepositoryFake()
        userRepository = UserRepositoryFake()
        messages = Messages()

        teamService = TeamServiceImpl(messages, userFinder, teamRepository, userRepository)
    }

    @Nested
    inner class CreateTeam {

        private val member = User(id = "member_id")

        @Test
        fun executorWithoutPerm_shouldDenied() {
            assertRuntimeError(messages.noPermission()) {
                teamService.createTeam(user, listOf())
            }
        }

        @Test
        fun membersIsEmpty_shouldCancel() {
            gainAdminPerm()

            assertThrows<RuntimeException> {
                teamService.createTeam(user, listOf())
            }.let {
                assertEquals(messages.membersCantBeEmpty(), it.message)
            }
        }

        @Test
        fun membersCouldNotFound_shouldCancel() {
            gainAdminPerm()

            val ids = listOf("1", "2")

            assertError<RuntimeException>(messages.membersCouldNotFound(ids)) {
                teamService.createTeam(user, ids)
            }
        }

        @Test
        fun membersAlreadyInTeam_shouldCancel() {
            gainAdminPerm()

            userFinder.add(member)
            teamRepository.createTeamWithUsers(member)

            assertError<RuntimeException>(messages.membersAlreadyInTeam(listOf(member))) {
                teamService.createTeam(user, listOf(member.id))
            }
        }


        @Test
        fun shouldCreateTeam() {
            gainAdminPerm()

            userFinder.add(member)

            val team = teamService.createTeam(user, listOf(member.id))

            assertEquals(listOf(member), team.members)
            assertEquals(teamRepository.findUsersTeam(member.id), team.toDBTeam())
        }
    }


    @Nested
    inner class ListTeams {

        @Test
        fun shouldListTeams() {
            val member = User(id = "member_id")
            val team = teamRepository.createTeamWithUsers(member).toTeam(listOf(member))

            userRepository.addUser(member)
            val teams = teamService.listTeams()

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
        user.isAdmin = true
    }
}