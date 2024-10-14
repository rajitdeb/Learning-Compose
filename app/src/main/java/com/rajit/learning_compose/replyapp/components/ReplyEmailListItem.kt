package com.rajit.learning_compose.replyapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rajit.learning_compose.R
import com.rajit.learning_compose.replyapp.data.Email

@Composable
fun ReplyEmailListItem(
    email: Email,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    navigateToDetail: (Long) -> Unit
) {
    /**
     * The email list on the home screen uses a card component. By default, it is a `Filled card`
     * that uses `surface variant` color for the `container color` to provide a clear separation
     * between surface and card color. Compose also provides implementations of `ElevatedCard`
     * and `OutlinedCard`.
     *
     * You can further highlight some items that are important by providing secondary color tones.
     * You'll modify `ui/components/ReplyEmailListItem.kt` by updating `card container color`
     * using `CardDefaults.cardColors()` for important emails:
     *
     * CODE:
     * Card(
     *    modifier =  modifier
     *        .padding(horizontal = 16.dp, vertical = 4.dp)
     *        .semantics { selected = isSelected }
     *        .clickable { navigateToDetail(email.id) },
     *    colors = CardDefaults.cardColors(
     *        containerColor = if (email.isImportant)
     *            MaterialTheme.colorScheme.secondaryContainer
     *        else MaterialTheme.colorScheme.surfaceVariant
     *    )
     * ){
     *   /*..*/
     * }
     */
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .semantics { selected = isSelected }
            .clickable { navigateToDetail(email.id) },
        colors = CardDefaults.cardColors(
            containerColor = if (email.isImportant)
                MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                ReplyProfileImage(
                    drawableResource = email.sender.avatar,
                    description = email.sender.fullName,
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = email.sender.firstName,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = email.createdAt,
                        style = MaterialTheme.typography.labelMedium,
                        // We want the time text to be less emphasized, therefore we're using variant color
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(
                    onClick = { /*Click Implementation*/ },
                    modifier = Modifier
                        .clip(CircleShape)

                ) {
                    Icon(
                        imageVector = Icons.Default.StarBorder,
                        contentDescription = stringResource(id = R.string.description_favorite),
                    )
                }
            }

            Text(
                text = email.subject,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            )
            Text(
                text = email.body,
                maxLines = 2,
                style = MaterialTheme.typography.bodyLarge,
                // We want the body text to be less emphasized, therefore we're using variant color
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}