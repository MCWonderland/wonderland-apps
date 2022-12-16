package org.mcwonderland.discord.commands

import net.dv8tion.jda.api.EmbedBuilder
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.commands.CommandHelpHandle

class HelpHandleImpl(private val commandPrefix: String) : CommandHelpHandle<DiscordCommandContext> {

    override fun showHelp(context: DiscordCommandContext, commands: List<Command>) {
        context.sendEmbed(EmbedBuilder().apply {
            setTitle("指令列表")
            setDescription("")
            commands.forEach { appendDescription("\n${commandPrefix}${it.usage}") }
        }.build())
    }

}