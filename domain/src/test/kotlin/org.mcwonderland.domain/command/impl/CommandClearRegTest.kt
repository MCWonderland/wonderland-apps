package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.features.RegistrationService

class CommandClearRegTest : CommandTestBase() {

    private lateinit var registrationService: RegistrationService

    @BeforeEach
    fun setup() {
        registrationService = mockk(relaxed = true)
        command = CommandClearReg("clearreg", registrationService, messages)
    }

    @Test
    fun shouldCallService() {
        executeWithNoArgs()
            .also { verify { registrationService.clearRegistrations(sender) } }
            .also { it.assertSuccess(messages.registrationsCleared()) }
    }

    @Test
    fun testExceptionMessageMappings() {
        assertExceptionMapping(PermissionDeniedException(), messages.noPermission())
    }

    private fun assertExceptionMapping(exception: Exception, noPermission: String) {
        every { registrationService.clearRegistrations(sender) } throws exception
        executeWithNoArgs().assertFail(noPermission)
    }


}