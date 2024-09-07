package com.rajit.learning_compose.composetoolings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rajit.learning_compose.R
import com.rajit.learning_compose.ui.theme.LearningComposeTheme

/**
 * Compose Shortcuts -
 * 1. Write `comp` for a sample compose template
 * 2. Write `WR` to surround a block of code with Row
 *
 * Migration from View system to Compose:
 * https://youtu.be/y10I6Suhvtc
 */
@Composable
fun SampleView(modifier: Modifier = Modifier) {
    Row {
        Image(
            painter = painterResource(id = R.drawable.fc2_nature_meditations),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(150.dp)
                .width(200.dp)
        )
        Text(text = "Spark", color = Color(0xFFFF8F00))
        RadioButton(selected = false, onClick = { /*TODO*/ })
    }
}

/**
 * To the left of the Preview annotation, we have the cog wheel icon
 * which can be used to add additional parameters easily to the preview
 *
 * LIVE EDIT is another cool feature where we don't need to rebuild our preview
 * every single time we need to change something.
 * Learn more about it here:
 * https://developer.android.com/develop/ui/compose/tooling#live-edit
 *
 * For more information about Preview Tooling and more:
 * https://www.youtube.com/watch?v=8XJfLaAOxD0
 */
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SampleViewPreview(modifier: Modifier = Modifier) {
    LearningComposeTheme {
        SampleView()
    }
}

@CustomFontScalePreviews
@CustomUIModePreviews
@Composable
fun SampleViewCustomPreview(modifier: Modifier = Modifier) {
    LearningComposeTheme {
        SampleView()
    }
}

