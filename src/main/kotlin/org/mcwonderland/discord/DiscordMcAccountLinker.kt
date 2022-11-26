package org.mcwonderland.discord

import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.AccountLinker
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.UserRepository

class DiscordMcAccountLinker(
    private val mojangAccount: MojangAccount,
    private val userRepository: UserRepository,
    private val messages: Messages
) : AccountLinker {

    override fun link(user: User, platformId: String) {
        if (user.mcId.isNotEmpty())
            throw RuntimeException(messages.accountAlreadyLinked())

        if (!mojangAccount.isAccountExist(platformId))
            throw RuntimeException(messages.accountNotFound())

        if (userRepository.findUserByMcId(platformId) != null)
            throw RuntimeException(messages.targetAccountAlreadyLink())

        userRepository.updateMcId(user.id, platformId)
    }

}