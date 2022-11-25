package org.mcwonderland.discord

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.exception.PermissionDeniedException
import org.mcwonderland.domain.fakes.TeamRepositoryFake
import org.mcwonderland.domain.fakes.UserFinderFake
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.TeamRepository
import kotlin.test.assertEquals

internal class TeamServiceDiscordTest {

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

        teamService = TeamServiceDiscord(messages, userFinder, teamRepository)
    }

    @Nested
    inner class CreateTeam {

        @Test
        fun executorWithoutPerm_shouldDenied() {
            assertThrows<PermissionDeniedException> {
                teamService.createTeam(user, listOf())
            }
        }

        @Test
        fun membersIsEmpty_shouldCancel() {
            user.isAdmin = true

            assertThrows<RuntimeException> {
                teamService.createTeam(user, listOf())
            }.let {
                assertEquals(messages.membersCantBeEmpty(), it.message)
            }
        }

        @Test
        fun membersCouldNotFound_shouldCancel() {
            user.isAdmin = true

            val ids = listOf("1", "2")

            assertThrows<RuntimeException> {
                teamService.createTeam(user, ids)
            }.let {
                assertEquals(messages.membersCouldNotFound(ids), it.message)
            }
        }

        @Test
        fun membersAlreadyInTeam_shouldCancel() {
            user.isAdmin = true

            val member = User(id = "1")
            userFinder.add(member)
            teamRepository.createTeamWithUsers(member)

            assertThrows<RuntimeException> {
                teamService.createTeam(user, listOf(member.id))
            }.let {
                assertEquals(messages.membersAlreadyInTeam(listOf(member.id)), it.message)
            }
        }
    }
}