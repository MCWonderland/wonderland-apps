package org.mcwonderland.domain.features

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.assertError
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.exception.PermissionDeniedException
import org.mcwonderland.domain.fakes.TeamRepositoryFake
import org.mcwonderland.domain.fakes.UserFinderFake
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.model.toDBTeam
import kotlin.test.assertEquals

internal class TeamServiceImplTest {

    private lateinit var teamService: TeamService

    private lateinit var messages: Messages
    private lateinit var userFinder: UserFinderFake
    private lateinit var teamRepository: TeamRepositoryFake

    private lateinit var user: User

    @BeforeEach
    fun setup() {
        user = User()
        userFinder = UserFinderFake()
        teamRepository = TeamRepositoryFake()
        messages = Messages()

        teamService = TeamServiceImpl(messages, userFinder, teamRepository)
    }

    @Nested
    inner class CreateTeam {

        private val member = User(id = "member_id")

        @Test
        fun executorWithoutPerm_shouldDenied() {
            assertThrows<PermissionDeniedException> {
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

            assertError<RuntimeException>(messages.membersAlreadyInTeam(listOf(member.id))) {
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

        private fun gainAdminPerm() {
            user.isAdmin = true
        }
    }

}