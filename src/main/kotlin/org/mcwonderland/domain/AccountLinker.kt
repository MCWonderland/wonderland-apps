package org.mcwonderland.domain

import org.mcwonderland.domain.model.CommandSender

interface AccountLinker {
    fun link(commandSender: CommandSender, platformId: String)
}
