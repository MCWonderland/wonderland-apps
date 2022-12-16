package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.User

interface CommandContext {
    val sender: User
    val label: String
    val args: List<String>

    fun getArg(index: Int): String? = args.getOrNull(index)
    fun subArgs(from: Int, to: Int = args.size): List<String> = args.subList(from, to)

    fun checkAdminPermission() {
        sender.checkAdminPermission()
    }
}
