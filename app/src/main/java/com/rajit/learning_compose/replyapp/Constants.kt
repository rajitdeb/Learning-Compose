package com.rajit.learning_compose.replyapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.People
import com.rajit.learning_compose.R

object Constants {

    val TOP_LEVEL_DESTINATIONS = listOf(
        ReplyTopLevelDestination(
            route = ReplyRoute.INBOX,
            selectedIcon = Icons.Default.Inbox,
            unselectedIcon = Icons.Default.Inbox,
            iconTextId = R.string.tab_inbox
        ),
        ReplyTopLevelDestination(
            route = ReplyRoute.ARTICLES,
            selectedIcon = Icons.Default.Article,
            unselectedIcon = Icons.Default.Article,
            iconTextId = R.string.tab_article
        ),
        ReplyTopLevelDestination(
            route = ReplyRoute.DM,
            selectedIcon = Icons.Default.ChatBubbleOutline,
            unselectedIcon = Icons.Default.ChatBubbleOutline,
            iconTextId = R.string.tab_dm
        ),
        ReplyTopLevelDestination(
            route = ReplyRoute.GROUPS,
            selectedIcon = Icons.Default.People,
            unselectedIcon = Icons.Default.People,
            iconTextId = R.string.tab_groups
        )
    )

}