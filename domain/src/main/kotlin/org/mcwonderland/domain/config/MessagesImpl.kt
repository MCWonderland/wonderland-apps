package org.mcwonderland.domain.config

import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.model.AddToTeamResult
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
            ids.map { discordTag(it) }.joinToString(", ")
        }"

    override fun accountAlreadyLinked(id: String): String = "你的帳號已經連結至 (${mcName(id)})"
    override fun mcAccountWithIgnNotFound(ign: String): String = "找不到名子為 $ign 的 Minecraft 帳號"
    override fun targetAccountAlreadyLink(ign: String): String = "已經有使用者連結了 $ign 這隻 Minecraft 帳號"
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
            messages.add("隊伍: ${team.id}")
            team.members.forEach { messages.add("> " + tagAndName(it)) }
        }

        return messages.joinToString("\n")
    }

    override fun noPermission(): String = "你沒有權限執行這個操作"
    override fun userNotFound(targetId: String): String = "找不到使用者的數據: ${discordTag(targetId)}"

    override fun userNotInTeam(target: User): String {
        return "使用者 ${discordTag(target)} 不在任何隊伍當中"
    }

    override fun userRemovedFromTeam(expectTeam: Team): String {
        return "使用者已經從隊伍中移除，隊伍成員剩下: ${
            expectTeam.members.joinToString(", ") { tagAndName(it) }
        }"
    }

    override fun membersNotLinked(listOf: List<User>): String {
        return "以下成員沒有綁定帳號: ${
            listOf.map { discordTag(it) }.joinToString(", ")
        }"
    }

    override fun requireLinkedAccount(): String {
        return "請先綁定帳號後，再使用這個功能。"
    }

    override fun linked(user: User): String {
        return "${discordTag(user)} 已經綁定帳號: ${mcName(user)}"
    }

    override fun registered(): String {
        return "已成功報名 Weekly Cup! 感謝你的參與"
    }

    override fun unRegistered(): String {
        return "已取消報名"
    }

    override fun listRegistrations(users: Collection<User>): String {
        val messages = mutableListOf<String>()

        messages.add("報名列表:")
        messages.add(" ")
        users.forEach { messages.add("> " + tagAndName(it)) }

        return messages.joinToString("\n")
    }

    override fun unHandledCommandError(exceptionClassName: String): String {
        return "發生了一個未處理的錯誤: $exceptionClassName, 請聯絡管理員"
    }

    override fun addedUserToTeam(result: AddToTeamResult): String {
        val messages = mutableListOf<String>()

        messages.add("已經將 ${tagAndName(result.user)} 加入隊伍")
        messages.add("目前隊伍成員:")
        messages.addAll(teamMembers(result.team))

        return messages.joinToString("\n")
    }

    override fun userAlreadyInTeam(user: User): String {
        return "使用者 ${discordTag(user)} 已經在隊伍當中了"
    }

    override fun teamNotFound(teamId: String): String {
        return "找不到隊伍: $teamId"
    }

    override fun teamDeleted(teamId: String): String {
        return "已經刪除隊伍: $teamId"
    }

    private fun teamMembers(team: Team): List<String> {
        return team.members.map { "> " + tagAndName(it) }
    }

    private fun tagAndName(user: User): String {
        return "${discordTag(user)}(${mcName(user)})"
    }

    private fun discordTag(user: User) = discordTag(user.discordId)
    private fun discordTag(id: String): String = "<@${id}>"
    private fun mcName(user: User): String = mojangAccount.getNameByUUID(user.mcId) ?: "未知的 ID"

    private fun mcName(uuid: String) = mojangAccount.getNameByUUID(uuid) ?: "未知的 ID"
}