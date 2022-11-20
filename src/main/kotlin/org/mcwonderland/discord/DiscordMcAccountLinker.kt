package org.mcwonderland.discord

import org.mcwonderland.domain.AccountFinder
import org.mcwonderland.domain.AccountLinker
import org.mcwonderland.domain.UserCreator
import org.mcwonderland.domain.exception.AccountAlreadyOwnedException
import org.mcwonderland.domain.exception.AccountNotExistException
import org.mcwonderland.domain.exception.AlreadyLinkedException
import org.mcwonderland.domain.model.CommandSender
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.UserRepository

class DiscordMcAccountLinker(
    private val accountFinder: AccountFinder,
    private val userCreator: UserCreator,
    private val userRepository: UserRepository,
) : AccountLinker {

    override fun link(commandSender: CommandSender, platformId: String) {
        val user = userRepository.findUserByDiscordId(commandSender.id) ?: userCreator.create()

        if (user.mcId.isNotEmpty())
            throw AlreadyLinkedException()

        if (!accountFinder.isAccountExist(platformId))
            throw AccountNotExistException()

        if (userRepository.findUserByMcId(platformId) != null)
            throw AccountAlreadyOwnedException()

        userRepository.updateMcId(user.id, platformId)
    }

}