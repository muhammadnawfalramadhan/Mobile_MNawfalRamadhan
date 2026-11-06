package com.example.nawfal_3tif.Message

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MessageModel(
    val senderName: String,
    val messageText: String,
    val avatarUrl: String,
    val timestamp: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
)
