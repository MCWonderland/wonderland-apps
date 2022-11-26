package org.mcwonderland.domain.config

import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

interface Messages {
    fun membersCantBeEmpty(): String
    fun membersCouldNotFound(ids: List<String>): String
    fun membersAlreadyInTeam(ids: List<User>): String
    fun accountAlreadyLinked(): String
    fun accountNotFound(): String
    fun targetAccountAlreadyLink(): String
    fun invalidArg(argName: String): String
    fun teamCreated(team: Team): String
    fun teamList(teams: List<Team>): String
    fun noPermission(): String
    fun userNotFound(targetId: String): String
    fun userNotInTeam(target: User): String
    fun userRemovedFromTeam(expectTeam: Team): String
}

class MessagesImpl : Messages {

    override fun membersCantBeEmpty(): String = "請輸入隊伍成員"
    override fun membersCouldNotFound(ids: List<String>): String =
        "無法找到以下成員的資料，他們或許還沒綁定: ${
            ids.map { userTag(it) }
                .joinToString(", ")
        }"

    override fun membersAlreadyInTeam(ids: List<User>): String =
        "以下成員已經在隊伍當中了: ${
            ids.map { userTag(it.discordId) }.joinToString(", ")
        }"

    override fun accountAlreadyLinked(): String = "你的帳號已經連結過了"
    override fun accountNotFound(): String = "找不到這個帳號"
    override fun targetAccountAlreadyLink(): String = "目標帳號已經連結過了"
    override fun invalidArg(argName: String): String = "缺少或是無效的參數: $argName"
    override fun teamCreated(team: Team): String =
        "隊伍已經建立，成員: ${
            team.members.map { userTag(it.discordId) }
                .joinToString(", ")
        }"

    private fun userTag(id: String): String = "<@${id}>"
    override fun teamList(teams: List<Team>): String {
        return teams.mapIndexed { index, team ->
            "隊伍 ${index}, 成員: ${team.members.map { userTag(it.discordId) }.joinToString(", ")}"
        }.joinToString("\n")
    }

    override fun noPermission(): String = "你沒有權限執行這個操作"
    override fun userNotFound(targetId: String): String = "找不到使用者的數據: ${userTag(targetId)}"

    override fun userNotInTeam(target: User): String {
        return "使用者 ${userTag(target.discordId)} 不在任何隊伍當中"
    }

    override fun userRemovedFromTeam(expectTeam: Team): String {
        return "使用者已經從隊伍中移除，隊伍成員剩下: ${
            expectTeam.members.joinToString(", ") { userTag(it.discordId) }
        }"
    }
}