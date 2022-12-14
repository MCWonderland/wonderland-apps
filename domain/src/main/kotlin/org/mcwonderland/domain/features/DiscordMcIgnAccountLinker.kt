package org.mcwonderland.domain.features

import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.exceptions.AccountAlreadyLinkedException
import org.mcwonderland.domain.exceptions.MCAccountLinkedByOthersException
import org.mcwonderland.domain.exceptions.MCAccountNotFoundException
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.UserRepository

class DiscordMcIgnAccountLinker(
    private val mojangAccount: MojangAccount,
    private val userRepository: UserRepository,
) : AccountLinker {

    override fun link(user: User, userIgn: String): User {
        if (isLinked(user))
            throw AccountAlreadyLinkedException(user.mcProfile.uuid)

        val uuid = mojangAccount.getUUIDByName(userIgn) ?: throw MCAccountNotFoundException(userIgn)

        if (userRepository.findUserByMcId(uuid.toString()) != null)
            throw MCAccountLinkedByOthersException(userIgn)

        return userRepository.updateMcId(user.id, uuid.toString())!!
    }

    override fun isLinked(user: User): Boolean {
        return user.mcProfile.hasUuid()
    }

}