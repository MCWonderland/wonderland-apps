package org.mcwonderland.domain.config

class Messages {

    fun membersCantBeEmpty(): String = "請輸入隊伍成員"
    fun membersCouldNotFound(ids: List<String>): String = "無法找到以下成員: ${ids.joinToString { ", " }}"
    fun membersAlreadyInTeam(ids: List<String>): String = "以下成員已經在隊伍當中了: ${ids.joinToString { ", " }}"

    fun accountAlreadyLinked(): String = "你的帳號已經連結過了"
    fun accountNotFound(): String = "找不到這個帳號"
    fun targetAccountAlreadyLink(): String = "目標帳號已經連結過了"
    fun invalidArg(argName: String): String = "缺少或是無效的參數: $argName"

}