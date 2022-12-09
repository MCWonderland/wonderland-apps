package org.mcwonderland.domain.repository

interface SettingsRepository {
    fun isAllowRegistrations(): Boolean
    fun setAllowRegistrations(state: Boolean): Boolean
}