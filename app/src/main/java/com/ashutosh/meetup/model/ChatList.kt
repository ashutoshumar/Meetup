package com.ashutosh.meetup.model

import java.sql.ClientInfoStatus

data class ChatList(
    val chatUserId:String,
    val chatUserName:String,
    val chatProfileImage:String,
    val chatLastSeen:String,
    val status:String
)