package com.rajit.learning_compose.lazylistapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowUp
import androidx.compose.material.icons.filled.SwipeLeftAlt
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.rajit.learning_compose.ui.theme.LearningComposeTheme
import kotlinx.coroutines.launch

val sampleMutableList = mutableListOf<String>().toMutableStateList()

/**
 * USEFUL TIPS WHILE WORKING WITH LAZY LIST:
 *
 * 1. Don't use 0-pixel sized items. What it means is that give an appropriate width and height
 * to the item. Let's say we have an image that is fetched from the internet
 * so until the image is fetched successfully the list will consider the image to be 0-pixel sized
 * and when it is fetched successfully, the list will recompose and might change the scroll position
 * where user had scrolled to. Make it an undesirable user experience.
 * Always use a Placeholder to handle this scenario.
 * For more info, visit (https://youtu.be/1ANt65eoNhQ?t=794)
 *
 * 2. Avoid nesting components scrollable in the same direction.
 * Let's take an example, where we declare a LazyColumn (scrollable vertically) without a fixed height
 * and its parent is a Column (which is also vertically scrollable) also without a fixed height.
 * This scenario is to be avoided at any cost. And this can be done in a way where you declare
 * either of the item/component as fixed height (Column/LazyColumn whichever you want).
 *
 * 3. Beware of putting multiple elements in one item. Like so:
 * LazyVerticalGrid(
 *      ...
 * ) {
 *      item { Item(0) }
 *      item {
 *          Item(1)
 *          Item(2)
 *      }
 *      item { Item(3) }
 * }
 *
 * Here, the LazyList would handle this scenario gracefully for us.
 * But the problem arises -
 * i. They are treated as one element. Which means if one element is visible on the screen
 * then Compose will consider it as one element and render all the items that are in that block
 * even if they are not yet visible on the screen. Therefore, unnecessary recompositions.
 *
 * ii. scrollToItem() and animateScrollToItem() will also behave absurdly
 * on runtime. If we want to scroll to Item 2 and we pass the index as 1 (0-based index)
 * we'll rather see Item 3 (Text 2) on the screen rather than Item 2 (Text 1)
 * IMPORTANT:
 * We should also ensure that we don't provide divider a separate item block
 * rather keep it together with other items. Like so:
 * LazyVerticalGrid(
 *      ...
 * ) {
 *      item { Item(0) }
 *      item {
 *          Item(1)
 *          Divider()
 *      }
 *      item { Item(2) }
 * }
 *
 */
@Composable
fun LazyListApp() {
    LearningComposeTheme {
        Scaffold { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                // MyLazyColumn()
                // MyLazyRow()
                MyLazyVerticalGrid()
            }
        }
    }
}

@Composable
fun MyLazyVerticalGrid() {

    LaunchedEffect(sampleMutableList) {
        sampleMutableList.addAll(Constants.sampleData)
    }

    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val showScrollToTopButton by remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex > 1
        }
    }

    /**
     * Custom Sizing of Columns
     *
     * Here, we take a use case where we want our `firstColumn` to take 2/3 width
     * and `secondColumn` take the remaining width
     */
    val customGridCells = object : GridCells {
        override fun Density.calculateCrossAxisCellSizes(
            availableSize: Int,
            spacing: Int
        ): List<Int> {
            val firstColumn = (availableSize - spacing) * 2 / 3
            val secondColumn = (availableSize - spacing - firstColumn)
            return listOf(firstColumn, secondColumn)
        }

    }

    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        LazyVerticalGrid(
            state = gridState,
            // Fixed is used when we know the dimensions of all of our items accurately
            // For e.g. Width = 100.dp, Height = 100.dp
//            columns = GridCells.Fixed(2),
            /*
             * Adaptive is used when we don't know the exact dimensions of all the items
             * Or maybe we want our UI to be flexible to accommodate enough items on orientation change
             * or screen size changes
             *
             * Here, it take the width of each item in order to accommodate enough items on the screen
             * without having to stretch out or crop content from the screen and is flexible enough
             */
//            columns = GridCells.Adaptive(120.dp),
            columns = customGridCells,
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            item(
                /*
                 * Span is used to provide a custom sizing to a component
                 * Like for example, here we want a header for the items
                 * and we want it to span to the entire width of the screen
                 * `maxLineSpan` is used to specify the maximum columns (in Vertical Grid)
                 * and number of rows (in Horizontal Grid) the composable
                 * should occupy
                 *
                 * We also have `currentLineSpan`
                 * The max current line (horizontal for vertical grids) the item can occupy,
                 * such that it will be positioned on the current line.
                 * For example if LazyVerticalGrid has 3 columns this value will be 3
                 * for the first cell in the line, 2 for the second cell, and 1 for the last one.
                 * If you return a span count larger than maxCurrentLineSpan
                 * this means we can't fit this cell into the current line,
                 * so the cell will be positioned on the next line.
                 */
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Text Items")
                    }
                }
            }

            items(
                sampleMutableList,
                // This is really important while animating the item placement/reordering
                // Also this needs to be unique otherwise it will throw runtime exception
                // IMPORTANT: key can only hold values that are supported by Bundle
                // For example, primitive types, enums, Parcelable etc.
                key = { it }
            ) { item ->
                LazyGridItem(
                    item = item,
                    // This was old way of animating Item Placement
//                    modifier = Modifier.animateItemPlacement(
//                        tween(durationMillis = 250)
//                    )
                    // Current way of animating Item Placement, Addition and Removal
                    // animateItem() is the method that animates Item Placement, and Reordering
                    modifier = Modifier.animateItem()
                        .clickable { sampleMutableList.add("Text 14") }
                )
            }
        }

        // As the name suggests `AnimatedVisibility` will animate the visibility of the composable
        // based on some condition
        AnimatedVisibility(showScrollToTopButton) {
            ScrollToPositionButton(
                onClick = {
                    coroutineScope.launch {
                        // Scrolls the grid scroll position to the specified index
                        // gridState.scrollToItem(0)

                        // Does the exact same thing as `scrollToItem()` but with animation
                        gridState.animateScrollToItem(0)
                    }
                },
                imageVector = Icons.Default.KeyboardDoubleArrowUp
            )
        }
    }

}

@Composable
fun LazyGridItem(item: String, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .height(100.dp)
            .width(100.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(item)
        }
    }
}

@Composable
fun MyLazyColumn(modifier: Modifier = Modifier) {

    // LazyListState is used to remember/react to the scroll position
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        // ContentPadding is used to prevent that ugly clipping of items
        // near screen boundaries (that cuts off the items)
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // LazyListScope block
        item { // This is for adding 1 item to the list
            Text("LazyList App")
        }

        items(Constants.sampleData) { item -> // This is for multiple items
            LazyListColumnItem(data = item)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyLazyRow(modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val showScrollToLeftButton by remember {
        // derivedStateOf is used to keep the calculation result saved in between compositions
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    Box(
        contentAlignment = Alignment.CenterEnd
    ) {
        LazyRow(
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(listState),
            // ContentPadding is used to prevent that ugly clipping of items
            // near screen boundaries (that cuts off the items)
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp
            ),
            // This is used provide spacing between items in the LazyList
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(Constants.sampleData) { item ->
                LazyListRowItem(item)
            }
        }

        AnimatedVisibility(showScrollToLeftButton) {
            ScrollToPositionButton(
                onClick = {
                    coroutineScope.launch {
//                        listState.scrollToItem(index = 0)
                        listState.animateScrollToItem(index = 0)
                    }
                },
                imageVector = Icons.Default.SwipeLeftAlt
            )
        }


    }
}

@Composable
fun ScrollToPositionButton(
    onClick: () -> Unit,
    imageVector: ImageVector
) {
    IconButton(
        onClick = { onClick() },
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = Color(0xFFFAC42F)
        )
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Move to First",
            tint = Color.White
        )
    }
}

@Composable
fun LazyListColumnItem(data: String) {
    Row(
        modifier = Modifier
            .background(Color.Red)
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(data)
    }
}

@Composable
fun LazyListRowItem(data: String) {
    Card(shape = RoundedCornerShape(16.dp)) {
        Row(
            modifier = Modifier
                .background(Color.Red)
                .height(120.dp)
                .padding(24.dp)
        ) {
            Text(data)
        }
    }
}