package org.mcwonderland.domain.command

interface CommandService {
    fun onCommand(label: String, args: List<String>)
}
