package org.mcwonderland.discord

import org.mcwonderland.domain.AccountLinker
import org.mcwonderland.domain.exception.AccountNotExistException
import org.mcwonderland.domain.model.CommandSender

class DiscordMCAccountLinker : AccountLinker {

    override fun link(commandSender: CommandSender, platformId: String) {
        throw AccountNotExistException()
    }

}