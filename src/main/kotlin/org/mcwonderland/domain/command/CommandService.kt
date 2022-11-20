package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.User

interface CommandService {
    fun onCommand(sender: User, label: String, args: List<String>)
}
