package org.mcwonderland.discord

import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.config.Messages
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
            throw RuntimeException(messages.accountAlreadyLinked("mcId"))

        val uuid = mojangAccount.getUUIDByName(userIgn) ?: throw RuntimeException(messages.mcAccountWithIgnNotFound("id"))

        if (userRepository.findUserByMcId(uuid.toString()) != null)
            throw RuntimeException(messages.targetAccountAlreadyLink("ign"))

        return userRepository.updateMcId(user.id, uuid.toString())!!
    }

    override fun isLinked(user: User): Boolean {
        return user.mcId.isNotEmpty()
    }

}