package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.model.User

internal class CommandListRegTest : CommandTestBase() {

    private lateinit var registrationService: RegistrationService

    @BeforeEach
    fun setup() {
        registrationService = mockk(relaxed = true)
        command = CommandListReg("listreg", registrationService, messages)
    }

    @Test
    fun shouldCallService() {
        val expectUsers = listOf(User(), User())
        every { registrationService.listRegistrations(sender) } returns expectUsers

        executeWithNoArgs().assertSuccess(messages.listRegistrations(expectUsers))
    }


}