package org.mcwonderland.discord

import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.exceptions.AccountAlreadyLinkedException
import org.mcwonderland.domain.exceptions.MCAccountLinkedByOthersException
import org.mcwonderland.domain.exceptions.MCAccountNotFoundException
import org.mcwonderland.domain.features.AccountLinker
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.UserRepository

class DiscordMcIgnAccountLinker(
    private val mojangAccount: MojangAccount,
    private val userRepository: UserRepository,
    private val messages: Messages
) : AccountLinker {

    override fun link(user: User, userIgn: String): User {
        if (user.mcId.isNotEmpty())
            throw AccountAlreadyLinkedException(user.mcId)

        val uuid = mojangAccount.getUUIDByName(userIgn) ?: throw MCAccountNotFoundException(userIgn)

        if (userRepository.findUserByMcId(uuid.toString()) != null)
            throw MCAccountLinkedByOthersException(userIgn)

        return userRepository.updateMcId(user.id, uuid.toString())!!
    }

    override fun isLinked(user: User): Boolean {
        return user.mcId.isNotEmpty()
    }

}