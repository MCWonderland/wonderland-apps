package org.mcwonderland.domain.config

import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

class Messages {

    fun membersCantBeEmpty(): String = "請輸入隊伍成員"
    fun membersCouldNotFound(ids: List<String>): String =
        "無法找到以下成員的資料，他們或許還沒綁定: ${
            ids.map { userTag(it) }
                .joinToString(", ")
        }"

    fun membersAlreadyInTeam(ids: List<User>): String =
        "以下成員已經在隊伍當中了: ${
            ids.map { userTag(it.discordId) }.joinToString(", ")
        }"

    fun accountAlreadyLinked(): String = "你的帳號已經連結過了"
    fun accountNotFound(): String = "找不到這個帳號"
    fun targetAccountAlreadyLink(): String = "目標帳號已經連結過了"
    fun invalidArg(argName: String): String = "缺少或是無效的參數: $argName"
    fun teamCreated(team: Team): String =
        "隊伍已經建立，成員: ${
            team.members.map { userTag(it.discordId) }
                .joinToString(", ")
        }"

    private fun userTag(id: String): String = "<@${id}>"
    fun teamList(teams: List<Team>): String {
        return teams.mapIndexed { index, team ->
            "隊伍 ${index}, 成員: ${team.members.map { userTag(it.discordId) }.joinToString(", ")}"
        }.joinToString("\n")
    }

    fun noPermission(): String = "你沒有權限執行這個操作"
    fun userNotFound(targetId: String): String = "找不到使用者的數據: ${userTag(targetId)}"

    fun userNotInTeam(target: User): String {
        return "使用者 ${userTag(target.discordId)} 不在任何隊伍當中"
    }
}