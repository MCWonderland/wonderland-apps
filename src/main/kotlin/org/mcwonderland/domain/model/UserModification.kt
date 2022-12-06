package org.mcwonderland.domain.model

import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.features.UserFinder

data class UserModification(
    val executor: User,
    val targetId: String,
) {
    fun isExecutorAdministrator() = executor.isAdministrator()

    fun findTargetForce(userFinder: UserFinder): User {
        return userFinder.find(targetId) ?: throw UserNotFoundException(targetId)
    }
}
