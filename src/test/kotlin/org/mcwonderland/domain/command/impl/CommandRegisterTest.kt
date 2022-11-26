package org.mcwonderland.domain.command.impl

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.fakes.UserFinderStub
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser
import org.mcwonderland.domain.model.User

internal class CommandRegisterTest {

    private lateinit var command: CommandRegister
    private lateinit var registerService: RegistrationService
    private lateinit var userFinder: UserFinder

    private val user = User("user")

    @BeforeEach
    fun setUp() {
        registerService = mockk(relaxed = true)
        userFinder = UserFinderStub(user)

        command = CommandRegister("register", registerService, userFinder)
    }

    @Test
    fun shouldCallService() {
        val sender = PlatformUser("sender")
        command.execute(sender, emptyList())

        verify { registerService.register(user) }
    }
}