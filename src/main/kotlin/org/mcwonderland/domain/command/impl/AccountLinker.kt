package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.model.CommandSender

interface AccountLinker {
    fun link(commandSender: CommandSender, platformId: String)
}
