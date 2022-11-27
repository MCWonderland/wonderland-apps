package org.mcwonderland.domain.config

import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

class MessagesImpl(private val mojangAccount: MojangAccount) : Messages {

    override fun membersCantBeEmpty(): String = "請輸入隊伍成員"
    override fun membersCouldNotFound(ids: List<String>): String =
        "無法找到以下成員的資料，他們或許還沒綁定: ${
            ids.map { discordTag(it) }
                .joinToString(", ")
        }"

    override fun membersAlreadyInTeam(ids: List<User>): String =
        "以下成員已經在隊伍當中了: ${
            ids.map { discordTag(it.discordId) }.joinToString(", ")
        }"

    override fun accountAlreadyLinked(): String = "你的帳號已經連結過了"
    override fun accountNotFound(): String = "找不到這個帳號"
    override fun targetAccountAlreadyLink(): String = "目標帳號已經連結過了"
    override fun invalidArg(argName: String): String = "缺少或是無效的參數: $argName"
    override fun teamCreated(team: Team): String =
        "隊伍已經建立，成員: ${
            team.members.map { tagAndName(it) }.joinToString(", ")
        }"

    override fun teamList(teams: List<Team>): String {
        val messages = mutableListOf<String>()
        messages.add("隊伍列表:")

        teams.forEachIndexed { index, team ->
            messages.add(" ")
            messages.add("隊伍: ${index + 1}")
            team.members.forEach { messages.add("> " + tagAndName(it)) }
        }

        return messages.joinToString("\n")
    }

    override fun noPermission(): String = "你沒有權限執行這個操作"
    override fun userNotFound(targetId: String): String = "找不到使用者的數據: ${discordTag(targetId)}"

    override fun userNotInTeam(target: User): String {
        return "使用者 ${discordTag(target.discordId)} 不在任何隊伍當中"
    }

    override fun userRemovedFromTeam(expectTeam: Team): String {
        return "使用者已經從隊伍中移除，隊伍成員剩下: ${
            expectTeam.members.joinToString(", ") { tagAndName(it) }
        }"
    }

    override fun membersNotLinked(listOf: List<User>): String {
        return "以下成員沒有綁定帳號: ${
            listOf.map { discordTag(it.discordId) }.joinToString(", ")
        }"
    }

    override fun yourAccountNotLinked(): String {
        return "你的帳號還沒有綁定"
    }

    override fun linked(user: User): String {
        return "${discordTag(user.discordId)} 已經綁定帳號: ${mcName(user)}"
    }

    override fun registered(): String {
        return "已成功報名 Weekly Cup! 感謝你的參與"
    }

    override fun unRegistered(): String {
        return "已取消報名"
    }


    private fun tagAndName(user: User): String {
        return "${discordTag(user.discordId)}(${mcName(user)})"
    }

    private fun discordTag(id: String): String = "<@${id}>"
    private fun mcName(user: User): String = mojangAccount.getNameByUUID(user.mcId) ?: "未知的 ID"
}