package com.rajit.learning_compose.sootheapp.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rajit.learning_compose.R
import com.rajit.learning_compose.sootheapp.util.Constants.alignYourBodyData
import com.rajit.learning_compose.sootheapp.util.Constants.favoriteCollectionsData
import com.rajit.learning_compose.ui.theme.LearningComposeTheme

/**
 * @param modifier
 * We're passing modifier as a parameter because this is a best practice as per Compose guidelines.
 * This allows the method's caller to modify the composable's look & feel, which makes it more flexible and reusable.
 */
@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    TextField(
        value = "",
        onValueChange = {}, // will implement the state later
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                // icon doesn't need content description, because placeholder of textfield
                // already describes it
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        placeholder = {
            Text(stringResource(R.string.placeholder_search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

/**
 * @param image Drawable resource ID is expected (e.g. R.drawable.placeholder)
 * @param text String resource ID is expected (e.g. R.string.placeholder_text)
 */
@Composable
fun AlignYourBodyElement(
    @DrawableRes image: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = image),

            /* contentDescription of the image to null,
             * as this image is purely decorative. The text below
             * the image describes enough of the meaning,
             * so the image does not need an extra description.
             */
            contentDescription = null,
            /*
             * The image needs to be scaled correctly. To do so,
             * we can use the Image's `contentScale` parameter.
             * There are several options, most notably:
             * i. ContentScale.Fit
             * ii. ContentScale.FillBounds
             * iii. ContentScale.Crop
             *
             * In this case, ContentScale.Crop is the correct one to use.
             */
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(id = text),
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

/**
 * @param image Drawable resource ID is expected (e.g. R.drawable.placeholder)
 * @param text String resource ID is expected (e.g. R.string.placeholder_text)
 */
@Composable
fun FavoriteCollectionCard(
    @DrawableRes image: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(255.dp) // as per UI design in Codelab
        ) {
            Image(
                painter = painterResource(id = image),
                /* contentDescription of the image to null,
                 * as this image is purely decorative. The text below
                 * the image describes enough of the meaning,
                 * so the image does not need an extra description.
                 */
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Text(
                stringResource(id = text),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}


@Composable
fun AlignYourBodyRow(modifier: Modifier = Modifier) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        /*
         * While scrolling LazyRow, the first and last visible items are
         * cut off on both sides of the screen.
         *
         * To maintain the same padding, but still scroll your content
         * within the bounds of your parent list without clipping it,
         * all lists provide a parameter to the LazyRow called contentPadding
         * and set it to 16.dp
         *
         * NOTE:
         * Make sure that your composable behaves correctly when you swipe through
         * the list. You can use interactive Preview to interact with your composable's Preview.
         * https://developer.android.com/develop/ui/compose/tooling#preview-interactive
         */
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(alignYourBodyData) { currentItem ->
            AlignYourBodyElement(
                image = currentItem.drawable,
                text = currentItem.text
            )
        }
    }
}

@Composable
fun FavoriteCollectionGrid(modifier: Modifier = Modifier) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        // This is used to add padding to the front of the first item and rear of the last item
        // so that the content doesn't cut off before the screen boundary
        contentPadding = PaddingValues(horizontal = 16.dp),
        // This is used to add padding between the items
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.height(168.dp)
    ) {
        items(favoriteCollectionsData) { currentItem ->
            FavoriteCollectionCard(
                image = currentItem.drawable,
                text = currentItem.text,
                modifier = Modifier.height(80.dp)
            )
        }
    }
}

/**
 * @param title expects title of the section to be passed
 * @param content expects content that must go into this slot
 *
 * For more information about Slots and slot-based APIs.
 * @see <a href="https://developer.android.com/jetpack/compose/layouts/basics#slot-based-layouts">Slot-based Layouts</a>
 */
@Composable
fun HomeSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(modifier = Modifier.padding(horizontal = 16.dp))

        // Align Your Body Section
        HomeSection(title = R.string.align_your_body) {
            AlignYourBodyRow()
        }

        // Favourite Collections Section
        HomeSection(title = R.string.favorite_collections) {
            FavoriteCollectionGrid()
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Spa,
                    contentDescription = null // Don't need content description because label is present
                )
            },
            label = { Text(text = stringResource(id = R.string.bottom_navigation_home)) },
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null // Don't need content description because label is present
                )
            },
            label = { Text(text = stringResource(id = R.string.bottom_navigation_profile)) },
        )
    }
}

@Composable
fun MySootheAppPortrait() {
    LearningComposeTheme {
        Scaffold(
            bottomBar = { SootheBottomNavigation() }
        ) { padding ->
            HomeScreen(Modifier.padding(padding))
        }
    }
}

/**
 * Navigation Rail will be used when the app is in Landscape/Tablet Mode
 * This makes the layout adaptive with respect to different screen sizes
 */
@Composable
private fun SootheNavigationRail(modifier: Modifier = Modifier) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        Column( // Used a column to center the items in the Navigation Rail
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationRailItem(
                selected = true,
                onClick = {},
                icon = {
                    Icon(
                        imageVector = Icons.Default.Spa,
                        contentDescription = null // Don't need content description because label is present
                    )
                },
                label = { Text(text = stringResource(id = R.string.bottom_navigation_home)) },
            )
            NavigationRailItem(
                selected = false,
                onClick = {},
                icon = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null // Don't need content description because label is present
                    )
                },
                label = { Text(text = stringResource(id = R.string.bottom_navigation_profile)) },
            )
        }
    }
}

@Composable
fun MySootheAppLandscape() {
    LearningComposeTheme {
        /*
         * For the portrait version of the app we've used a Scaffold.
         * However, for landscape we'll use a Row and
         * place the navigation rail and screen content next to each other.
         *
         * When we used a Scaffold in the portrait version,
         * it also took care of setting the content color to background for us.
         * To set the color of the Navigation Rail,
         * we wrap the Row in a `Surface` and set it to `background` color.
         */
        Surface(color = MaterialTheme.colorScheme.background) {
            Row {
                SootheNavigationRail()
                HomeScreen()
            }
        }
    }
}


@Composable
fun MySootheApp(windowSize: WindowSizeClass) {
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            MySootheAppPortrait()
        }

        WindowWidthSizeClass.Medium -> {
            MySootheAppLandscape()
        }

        WindowWidthSizeClass.Expanded -> {
            MySootheAppLandscape()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun SearchBarPreview() {
    LearningComposeTheme {
        SearchBar(Modifier.padding(8.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun AlignYourBodyElementPreview() {
    LearningComposeTheme {
        AlignYourBodyElement(
            R.drawable.ab1_inversions,
            R.string.ab1_inversions,
            Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun FavoriteCollectionCardPreview() {
    LearningComposeTheme {
        FavoriteCollectionCard(
            image = R.drawable.fc2_nature_meditations,
            text = R.string.fc2_nature_meditations,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun FavoriteCollectionsFridPreview() {
    LearningComposeTheme { FavoriteCollectionGrid() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun AlignYourBodyRowPreview() {
    LearningComposeTheme { AlignYourBodyRow() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun HomeSectionPreview() {
    LearningComposeTheme {
        HomeSection(title = R.string.align_your_body) {
            AlignYourBodyRow()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE, heightDp = 180)
@Composable
fun ScreenContentPreview() {
    LearningComposeTheme { HomeScreen() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun BottomNavigationPreview() {
    LearningComposeTheme {
        SootheBottomNavigation(Modifier.padding(top = 24.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun NavigationRailPreview() {
    LearningComposeTheme { SootheNavigationRail() }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MySoothePortraitPreview() {
    LearningComposeTheme { MySootheAppPortrait() }
}

@Preview(widthDp = 640, heightDp = 360)
@Composable
fun MySootheLandscapePreview() {
    LearningComposeTheme { MySootheAppLandscape() }
}