package com.rajit.learning_compose.replyapp

import androidx.compose.ui.graphics.vector.ImageVector

data class ReplyTopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)
