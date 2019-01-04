package com.utradia.catalogueappv2.model

data class NotificationResponse(
        val data: List<NotificationData>,
        val error_type: String,
        val success_message: String,
        val error_message: String,
        val success: Int
)

data class NotificationData(
        var created: String,
        val message: String,
        val id: String,
        val user_id: String,
        val title: String,
        val type: String,
        val action_id: String,
        val status: String,
        var header_format:String
)