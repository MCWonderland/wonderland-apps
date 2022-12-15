package org.mcwonderland.discord.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder
import org.mcwonderland.domain.commands.CommandLinkHandle
import org.mcwonderland.domain.exceptions.AccountAlreadyLinkedException
import org.mcwonderland.domain.exceptions.MCAccountLinkedByOthersException
import org.mcwonderland.domain.exceptions.MCAccountNotFoundException
import org.mcwonderland.domain.model.User

class CommandLinkHandleImpl : CommandLinkHandle {

    override fun missingArgId() {
        //create embed message with two buttons
        //one button is to link the account
        //another button is to cancel

        EmbedBuilder()
            .setTitle("Link your Minecraft account")
            .setDescription("Please enter your Minecraft username")
            .build()
    }

    override fun linked(userLinked: User) {
        TODO("Not yet implemented")
    }

    override fun failAccountAlreadyLinked(e: AccountAlreadyLinkedException) {
        TODO("Not yet implemented")
    }

    override fun failMcAccountNotFound(e: MCAccountNotFoundException) {
        TODO("Not yet implemented")
    }

    override fun failMcAccountLinkedByOthers(e: MCAccountLinkedByOthersException) {
        TODO("Not yet implemented")
    }

}