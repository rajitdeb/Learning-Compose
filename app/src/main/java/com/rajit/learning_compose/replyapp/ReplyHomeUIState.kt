package com.rajit.learning_compose.replyapp

import com.rajit.learning_compose.replyapp.data.Email

data class ReplyHomeUIState(
    val emails: List<Email> = emptyList(),
    val selectedEmail: Email? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = true,
    val error: String? = null
)
