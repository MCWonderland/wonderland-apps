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

    fun membersCantBeEmpty(): String = "請輸入隊伍成員"
    fun membersCouldNotFound(ids: List<String>): String =
        "無法找到以下成員的資料，他們或許還沒綁定: ${
            ids.map { discordTag(it) }
                .joinToString(", ")
        }"

    fun membersAlreadyInTeam(ids: List<User>): String =
        "以下成員已經在隊伍當中了: ${
            ids.map { discordTag(it) }.joinToString(", ")
        }"

    fun accountAlreadyLinked(id: String): MessageEmbed {
        return EmbedBuilder()
            .setTitle("錯誤")
            .setDescription("你已經綁定過帳號囉")
            .addField("已綁定的帳號", mcName(id), false)
            .build()
    }

    fun mcAccountWithIgnNotFound(ign: String): MessageEmbed {
        return EmbedBuilder()
            .setTitle("錯誤")
            .setDescription("找不到此 IGN 的 Minecraft 帳號")
            .addField("輸入的 IGN", ign, false)
            .build()
    }

    fun targetAccountAlreadyLink(ign: String): MessageEmbed {
        return EmbedBuilder()
            .setTitle("錯誤")
            .setDescription("此 Minecraft 帳號已經被其他使用者綁定了")
            .addField("已綁定的帳號", ign, false)
            .build()
    }

    fun invalidArg(argName: String): String = "缺少或是無效的參數: $argName"
    fun teamCreated(team: Team): String =
        "隊伍已經建立，成員: ${
            team.members.map { tagAndName(it) }.joinToString(", ")
        }"

    fun teamList(teams: List<Team>): String {
        val messages = mutableListOf<String>()
        messages.add("隊伍列表:")

        teams.forEachIndexed { index, team ->
            messages.add(" ")
            messages.add("隊伍: ${team.id}")
            team.members.forEach { messages.add("> " + tagAndName(it)) }
        }

        return messages.joinToString("\n")
    }

    fun noPermission(): String = "你沒有權限執行這個操作"
    fun userNotFound(targetId: String): String = "找不到使用者的數據: ${discordTag(targetId)}"

    fun userNotInTeam(target: User): String {
        return "使用者 ${discordTag(target)} 不在任何隊伍當中"
    }

    fun userRemovedFromTeam(expectTeam: Team): String {
        return "使用者已經從隊伍中移除，隊伍成員剩下: ${
            expectTeam.members.joinToString(", ") { tagAndName(it) }
        }"
    }

    fun membersNotLinked(listOf: List<User>): String {
        return "以下成員沒有綁定帳號: ${
            listOf.map { discordTag(it) }.joinToString(", ")
        }"
    }

    fun requireLinkedAccount(): String {
        return "請先綁定帳號後，再使用這個功能。"
    }

    fun linked(user: User): MessageEmbed {
        return EmbedBuilder()
            .setTitle("帳號綁定成功")
            .setDescription("你的帳號已經綁定至 ${mcName(user)}")
            .build()
    }

    fun registered(): String {
        return "已成功報名 Weekly Cup! 感謝你的參與"
    }

    fun unRegistered(): String {
        return "已取消報名"
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
        messages.addAll(teamMembers(result.team))

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

    fun notAllowRegistrations(): String {
        return "目前暫不開放報名"
    }

    private fun teamMembers(team: Team): List<String> {
        return team.members.map { "> " + tagAndName(it) }
    }

    private fun tagAndName(user: User): String {
        return "${discordTag(user)}(${mcName(user)})"
    }

    private fun discordTag(user: User) = discordTag(user.discordProfile.id)
    private fun discordTag(id: String): String = "<@${id}>"
    private fun mcName(user: User): String = mojangAccount.getNameByUUID(user.discordProfile.username) ?: "未知的 ID"

    private fun mcName(uuid: String) = mojangAccount.getNameByUUID(uuid) ?: "未知的 ID"
}