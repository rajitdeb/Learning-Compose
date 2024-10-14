package com.rajit.learning_compose

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rajit.learning_compose.lazylistapp.LazyListApp
import com.rajit.learning_compose.replyapp.ReplyApp
import com.rajit.learning_compose.replyapp.ReplyHomeUIState
import com.rajit.learning_compose.replyapp.ReplyHomeViewModel
import com.rajit.learning_compose.replyapp.data.LocalEmailsDataProvider
import com.rajit.learning_compose.ui.theme.LearningComposeTheme
import com.rajit.learning_compose.wellnessapp.WaterCounterApp

class MainActivity : ComponentActivity() {

    private val viewModel: ReplyHomeViewModel by viewModels()

    // WindowSizeClass is responsible for detecting screen size changes
    // And accordingly display the adequate screen composable base on the type of layout
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
//            val windowSizeClass = calculateWindowSizeClass(this)
//
//            // Water Counter App is used to understand the concept of State Management in Compose
//            WaterCounterApp(windowSizeClass)

//            LazyListApp()

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LearningComposeTheme {
                /**
                 * Material 3 represents elevation mainly using tonal color overlays.
                 * This is a new way to differentiate containers and surfaces from each other —
                 * increasing tonal elevation uses a more prominent tone — in addition to shadows.
                 *
                 * Tonal elevation at level 2 which takes color from the primary color slot.
                 * Elevation overlays in dark themes have also changed to tonal color overlays in Material Design 3. The overlay color comes from the primary color slot.
                 *
                 * The M3 Surface — the backing composable behind most M3 components —
                 * includes support for both tonal and shadow elevation:
                 *
                 * CODE:
                 * Surface(
                 *    modifier = modifier,
                 *    tonalElevation = {..}
                 *    shadowElevation = {..}
                 * ) {
                 *    Column(content = content)
                 * }
                 */
                Surface(tonalElevation = 5.dp) {
                    ReplyApp(
                        replyHomeUIState = uiState,
                        closeDetailScreen = { viewModel.closeDetailScreen() },
                        navigateToDetail = { emailID ->
                            viewModel.setSelectedEmail(emailID)
                        }
                    )
                }
            }

        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun ReplyAppPreviewLight() {
    LearningComposeTheme {
        ReplyApp(
            replyHomeUIState = ReplyHomeUIState(
                emails = LocalEmailsDataProvider.allEmails
            )
        )
    }
}