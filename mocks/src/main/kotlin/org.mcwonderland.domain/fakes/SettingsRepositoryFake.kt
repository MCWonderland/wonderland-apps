package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.repository.SettingsRepository

class SettingsRepositoryFake : SettingsRepository {
    private var allowRegistrations = false

    override fun isAllowRegistrations(): Boolean {
        return allowRegistrations
    }

    override fun setAllowRegistrations(state: Boolean): Boolean {
        allowRegistrations = state
        return allowRegistrations
    }

}