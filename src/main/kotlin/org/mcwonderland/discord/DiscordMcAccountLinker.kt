package org.mcwonderland.discord

import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.features.AccountLinker
import org.mcwonderland.domain.exception.AccountAlreadyOwnedException
import org.mcwonderland.domain.exception.AccountNotExistException
import org.mcwonderland.domain.exception.AlreadyLinkedException
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.UserRepository

class DiscordMcAccountLinker(
    private val mojangAccount: MojangAccount,
    private val userRepository: UserRepository,
) : AccountLinker {

    override fun link(user: User, platformId: String) {
        if (user.mcId.isNotEmpty())
            throw AlreadyLinkedException()

        if (!mojangAccount.isAccountExist(platformId))
            throw AccountNotExistException()

        if (userRepository.findUserByMcId(platformId) != null)
            throw AccountAlreadyOwnedException()

        userRepository.updateMcId(user.id, platformId)
    }

}