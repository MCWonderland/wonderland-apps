package org.mcwonderland.discord.config

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.model.AddToTeamResult
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User
import java.awt.Color

class Messages(private val mojangAccount: MojangAccount) {

    fun membersCantBeEmpty(): MessageEmbed {
        return EmbedBuilder()
            .setTitle("Error")
            .setDescription("Members can't be empty")
            .build()
    }

    fun membersCouldNotFound(ids: List<String>): MessageEmbed {
        return EmbedBuilder()
            .error()
            .setDescription("無法找到以下使用者的資料，或許他們還沒綁定")
            .addField("未找到的使用者", ids.joinToString("\n"), false)
            .build()
    }

    fun membersAlreadyInTeam(members: List<User>): MessageEmbed {
        return EmbedBuilder()
            .error()
            .setDescription("以下成員已經在隊伍中了:")
            .addField("成員", members.joinToString(", ") { discordTag(it) }, false)
            .build()
    }

    fun accountAlreadyLinked(id: String): MessageEmbed {
        return EmbedBuilder()
            .error()
            .setDescription("你已經綁定過帳號囉")
            .addField("已綁定的帳號", mcName(id), false)
            .build()
    }

    fun mcAccountWithIgnNotFound(ign: String): MessageEmbed {
        return EmbedBuilder()
            .error()
            .setDescription("找不到此 IGN 的 Minecraft 帳號")
            .addField("輸入的 IGN", ign, false)
            .build()
    }

    fun targetAccountAlreadyLink(ign: String): MessageEmbed {
        return EmbedBuilder()
            .error()
            .setDescription("此 Minecraft 帳號已經被其他使用者綁定了")
            .addField("已綁定的帳號", ign, false)
            .build()
    }

    fun invalidArg(argName: String): String = "缺少或是無效的參數: $argName"

    fun teamCreated(team: Team): MessageEmbed {
        return EmbedBuilder()
            .setTitle("隊伍已建立")
            .setDescription("隊伍已建立，請使用 `!team add` 指令來加入隊伍")
            .teamInfo(team)
            .build()
    }

    fun listTeams(teams: List<Team>): MessageEmbed {
        return EmbedBuilder()
            .setTitle("隊伍清單")
            .apply { teams.forEach { addField(it.id, teamMembers(it), false) } }
            .build()
    }

    fun noPermission(): MessageEmbed {
        return EmbedBuilder()
            .error()
            .setDescription("你沒有權限執行此指令")
            .build()
    }

    fun userNotFound(targetId: String): MessageEmbed {
        return EmbedBuilder()
            .error()
            .setDescription("找不到此使用者")
            .addField("使用者 ID", targetId, false)
            .build()
    }

    fun userNotInTeam(target: User): MessageEmbed {
        return EmbedBuilder()
            .error()
            .setDescription("使用者 ${discordTag(target)} 不在任何隊伍中")
            .build()
    }

    fun userRemovedFromTeam(expectTeam: Team): MessageEmbed {
        return EmbedBuilder()
            .setTitle("已將使用者從隊伍中移除")
            .teamInfo(expectTeam)
            .build()
    }

    fun membersNotLinked(listOf: List<User>): String {
        return "以下成員沒有綁定帳號: ${
            listOf.map { discordTag(it) }.joinToString(", ")
        }"
    }

    fun requireLinkedAccount(): MessageEmbed {
        return EmbedBuilder()
            .error()
            .setDescription("你必須先綁定帳號才能使用此指令")
            .build()
    }

    fun linked(user: User): MessageEmbed {
        return EmbedBuilder()
            .setTitle("帳號綁定成功")
            .setDescription("你的帳號已經綁定至 ${mcName(user)}")
            .build()
    }

    fun registered(): MessageEmbed {
        return EmbedBuilder()
            .setTitle("報名成功！")
            .setColor(Color.GREEN)
            .setDescription("已成功報名 Weekly Cup! 感謝你的參與")
            .setImage("https://media.discordapp.net/attachments/1007651659503112252/1048629137578938419/img.png")
            .build()
    }

    fun unRegistered(): MessageEmbed {
        return EmbedBuilder()
            .setTitle("已取消報名")
            .setDescription("期待你下次的參與！")
            .build()
    }

    fun listRegistrations(users: Collection<User>): String {
        val messages = mutableListOf<String>()

        messages.add("報名列表:")
        messages.add(" ")
        users.forEach { messages.add("> " + tagAndName(it)) }

        return messages.joinToString("\n")
    }

    fun unHandledCommandError(exceptionClassName: String): String {
        return "發生了一個未處理的錯誤: $exceptionClassName, 請聯絡管理員"
    }

    fun addedUserToTeam(result: AddToTeamResult): String {
        val messages = mutableListOf<String>()

        messages.add("已經將 ${tagAndName(result.user)} 加入隊伍")
        messages.add("目前隊伍成員:")
        messages.addAll(teamInfo(result.team))

        return messages.joinToString("\n")
    }

    fun userAlreadyInTeam(user: User): String {
        return "使用者 ${discordTag(user)} 已經在隊伍當中了"
    }

    fun teamNotFound(teamId: String): String {
        return "找不到隊伍: $teamId"
    }

    fun teamDeleted(teamId: String): String {
        return "已經刪除隊伍: $teamId"
    }

    fun registrationsCleared(): String {
        return "已經清除報名列表"
    }

    fun commandHelp(commands: List<Command>): String {
        val messages = mutableListOf<String>()

        messages.add("指令列表:")
        messages.add(" ")
        messages += commands.map { "> ${it.usage}" }

        return messages.joinToString("\n")
    }

    fun missingArg(s: String): MessageEmbed {
        return EmbedBuilder()
            .setTitle("缺少參數")
            .setDescription("缺少參數: $s")
            .build()
    }

    fun nowAcceptRegistrations(): String {
        return "開放報名！"
    }

    fun noLongerAcceptRegistrations(): String {
        return "已關閉報名"
    }

    fun notAllowRegistrations(): MessageEmbed {
        return EmbedBuilder()
            .error()
            .setDescription("目前暫時不開放報名，請隨時關注我們的公告頻道！")
            .build()
    }

    private fun teamInfo(team: Team): List<String> {
        return team.members.map { "> " + tagAndName(it) }
    }

    private fun tagAndName(user: User): String {
        return "${discordTag(user)}(${mcName(user)})"
    }

    private fun discordTag(user: User) = discordTag(user.discordProfile.id)
    private fun discordTag(id: String): String = "<@${id}>"
    private fun mcName(user: User): String = mojangAccount.getNameByUUID(user.mcProfile.uuid) ?: "未知的 ID"

    private fun mcName(uuid: String) = mojangAccount.getNameByUUID(uuid) ?: "未知的 ID"

    private fun EmbedBuilder.error(): EmbedBuilder {
        return setColor(Color.RED).setTitle("錯誤")
    }

    fun commandUsage(usage: String): MessageEmbed {
        return EmbedBuilder()
            .setColor(Color.CYAN)
            .setTitle("指令用法")
            .setDescription(usage)
            .build()
    }

    private fun EmbedBuilder.teamInfo(team: Team): EmbedBuilder {
        return this
            .addField("隊伍名稱", team.id, false)
            .addField("隊伍成員", teamMembers(team), false)
    }

    private fun teamMembers(team: Team): String {
        return team.members.joinToString("\n") { discordTag(it) + "(${mcName(it)})" }
    }
}