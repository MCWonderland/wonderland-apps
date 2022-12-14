package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.model.DiscordProfile
import org.mcwonderland.domain.model.McProfile
import org.mcwonderland.domain.model.User

class UserStub(
    override var id: String = "",
    override var mcProfile: McProfile = McProfile(),
    override var discordProfile: DiscordProfile = DiscordProfile(),
    override var isAdmin: Boolean = false
) : User() {
    fun addAdminPerm() {
        isAdmin = true
    }
}