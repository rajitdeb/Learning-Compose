package com.rajit.learning_compose.wellnessapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rajit.learning_compose.ui.theme.LearningComposeTheme

@Composable
fun WellnessTasksList(
    list: List<WellnessTask>,
    onCheckedTask: (WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessTask) -> Unit,
    modifier: Modifier = Modifier,
) {

    // LazyColumn has a parameter called state of type LazyListState
    // state: LazyListState = rememberLazyListState()
    // The composable function rememberLazyListState creates an initial state for the list
    // using rememberSaveable. When the Activity is recreated, the scroll state is maintained
    // without you having to code anything.
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            /**
             * The `items` method received a `key` parameter.
             * By default, each item's state is keyed against the position of the item
             * in the list.
             *
             * In a mutable list, this causes issues when the data set changes,
             * since items that change position effectively lose any remembered state.
             *
             * You can easily fix this by using the `id` of each `WellnessTaskItem` as the key
             * for each item.
             */
            /**
             * The `items` method received a `key` parameter.
             * By default, each item's state is keyed against the position of the item
             * in the list.
             *
             * In a mutable list, this causes issues when the data set changes,
             * since items that change position effectively lose any remembered state.
             *
             * You can easily fix this by using the `id` of each `WellnessTaskItem` as the key
             * for each item.
             */
            key = { task -> task.id }
        ) { task ->
            StatefulWellnessTaskItem(
                taskName = task.label,
                checked = task.checked,
                onCheckedChange = { checked ->
                    onCheckedTask(task, checked)
                },
                onClose = { onCloseTask(task) }
            )
        }
    }

}

@Composable
fun StatefulWellnessTaskItem(
    taskName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    /*
     * There is an issue, as you saw in a previous section,
     * that when an item leaves the Composition, state that was remembered is forgotten.
     * For items on a LazyColumn, items leave the Composition entirely when you scroll past them
     * and they're no longer visible.
     *
     * How do you fix this? Once again, use `rememberSaveable`.
     * Your state will survive the activity or process recreation using the
     * saved instance state mechanism. Thanks to how rememberSaveable works together with the LazyList,
     * your items are able to also survive leaving the Composition.
     *
     * Since, we've migrated the UI state and business logic (list & remove method)
     * to the ViewModel, so we're also migrating the checkedState to data class
     * for better testability and single-point state management by ViewModel.
     */
    // var checkedState by rememberSaveable { mutableStateOf(false) }

    StatelessWellnessTaskItem(
        taskName = taskName,
        checked = checked,
        onCheckedChange = { newValue -> onCheckedChange(newValue) },
        onClose = onClose,
        modifier = modifier
    )
}

@Composable
fun StatelessWellnessTaskItem(
    taskName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = taskName,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )

        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }

}

/**
 * For getting a better understanding of State,
 * we should first understand - What is a State?
 * Ans: Any value that changes over time is a state.
 *
 * Now, we need to understand - What causes the state to update?
 * In Android apps, **State** is updated in response to **Events**.
 *
 * Events are inputs generated from outside or inside an application, such as:
 * - The user interacting with the UI by, for example, pressing a button.
 * - Other factors, such as sensors sending a new value, or network responses.
 *
 * **NOTE**:
 * While the state of the app offers a description of what to display in the UI,
 * events are the mechanism through which the state changes, resulting in changes to the UI.
 *
 * To understand the core UI update loop cycle, take a look at:
 * https://developer.android.com/static/codelabs/jetpack-compose-state/img/f415ca9336d83142_856.png
 *
 * If a state change happens, Compose re-executes the affected composable functions
 * with the new state, creating an updated UI—this is called recomposition.
 * Compose also looks at what data an individual composable needs,
 * so that it only recomposes components whose data has changed and
 * skips those that are not affected.
 *
 * SOLUTION:
 * Use Compose's **State** and **MutableState** types to make state observable by Compose.
 *
 * Compose keeps track of each composable that reads State value properties and
 * triggers a recomposition when its value changes. You can use the **mutableStateOf** function
 * to create an observable MutableState.
 *
 * It receives an initial value as a parameter that is wrapped in a State object,
 * which then makes its value observable.
 */
//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    Column(modifier = modifier.padding(16.dp)) {
//        /* Very basic way of trying to save the state
//         * ALSO THE WRONG WAY :(
//         *
//         * As it would be reinstated on recomposition.
//         * Basically, it would get stuck in recomposition loop
//         *
//         * var count = 0
//         */
//
//        /*
//         * This would also not work because even this gets reinitialized
//         * during re-composition.
//         *
//         * val count: MutableState<Int> = mutableStateOf(0)
//         *
//         */
//
//        /**
//         *
//         * A value calculated by **remember** is stored in the Composition during the initial composition,
//         * and the stored value is kept across recompositions.
//         *
//         * **IMPORTANT**:
//         * While `remember` helps us retain state across recompositions,
//         * it's not retained across configuration changes.
//         * For this, we must use `rememberSaveable` instead of `remember`.
//         *
//         * `rememberSaveable` automatically saves any value that can be saved in a Bundle.
//         * For other values, you can pass in a custom saver object.
//         * For more information on Restoring state in Compose, check out the documentation.
//         *
//         * Here, we're using Kotlin Delegates to make the code simpler and easily readable
//         */
////        var count by remember { mutableIntStateOf(0) }
//        var count by rememberSaveable { mutableIntStateOf(0) }
//
//        // Alternatively, we could also explicitly mention the Type of Value to track
//        // val count: MutableState<Int> = remember { mutableIntStateOf(0) }
//
//        /*
//         * State hoisting in Compose is a pattern of moving state to a composable's caller
//         * to make a composable stateless. The general pattern for state hoisting in Jetpack Compose
//         * is to replace the state variable with two parameters:
//         *
//         * value: T - the current value to display
//         * onValueChange: (T) -> Unit - an event that requests the value to change with a new value T
//         * where this value represents any state that could be modified.
//         *
//         * The pattern where the state goes down, and events go up is called Unidirectional Data Flow (UDF), and state hoisting is how we implement this architecture in Compose.
//         */
//
//        if (count > 0) {
//
//            /**
//             * Here, `count` and `showTask` are remembered values
//             *
//             * Now, follow these steps -
//             *
//             * 1. Press the `Add One` button. That increments `count` (this causes a recomposition)
//             * and both `WellnessTaskItem` and counter `Text` start to display.
//             *
//             * 2. Press the `X` of `WellnessTaskItem` component (this causes another recomposition).
//             * `showTask` is now `false`, which means `WellnessTaskItem` isn't displayed anymore.
//             *
//             * 3. Now, again press the `Add One` button (another recomposition occurs)
//             * `showTask` remembers you've closed `WellnessTaskItem` in the next recomposition
//             * if you keep adding glasses, and it won't display `WellnessTaskItem` anymore.
//             *
//             * 4. Press the `Clear Water Count` button to reset `count` to 0 and cause a recomposition.
//             * `Text` showing `count`, and all code related to `WellnessTaskItem`, are not invoked
//             * and leave the Composition.
//             *
//             * 5. `showTask` is forgotten because the code location where remember `showTask` is called
//             * was not invoked. You're back to the first step.
//             *
//             * 6. Now, if we press the `Add One` button making `count` greater than 0 (recomposition).
//             *
//             * 7. `WellnessTaskItem` composable displays again, because the previous value of `showTask`
//             * was forgotten when it left the Composition above.
//             *
//             *
//             * What if we require `showTask` to persist after `count` goes back to 0,
//             * longer than what `remember` allows (that is, even if the code location where
//             * `remember` is called is not invoked during a recomposition)?
//             *
//             */
//            var showTask by remember { mutableStateOf(true) }
//            if (showTask) {
//                WellnessTaskItem(
//                    taskName = "Have you taken your 15 minutes walk today?",
//                    // Use the WellnessTaskItem's onClose lambda function,
//                    // so that when the X button is pressed, the variable showTask changes to false
//                    // and the task isn't shown anymore.
//                    onClose = { showTask = false }
//                )
//            }
//
//            /*
//             * IMPORTANT:
//             * If a composable function is called during the initial composition or in recompositions,
//             * we say it is present in the Composition.
//             *
//             * A composable function that is not called—for example,
//             * because the function is called inside an if statement and
//             * the condition is not met—-is absent from the Composition.
//             */
//            Text("You've had $count glasses.")
//
//        }
//
//        Row(
//            modifier = Modifier.padding(top = 8.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            Button(
//                onClick = { count++ },
//                enabled = (count > 10)
//            ) {
//                Text("Add One")
//            }
//
//            Button(
//                onClick = { count = 0 },
//                enabled = (count > 0)
//            ) {
//                Text("Clear Water Count")
//            }
//        }
//    }
//}

/**
 * `StatefulCounter` owns the `state`. That means that it holds the `count state` and
 * modifies it when calling the `StatelessCounter` function.
 *
 * This is how the `State Hoisting` is done in Jetpack Compose.
 *
 * **IMPORTANT**:
 * When hoisting state, there are three rules to help you figure out where state should go:
 *
 * 1. State should be hoisted to at least the lowest common parent of all composables that use
 * the state (read).
 *
 * 2. State should be hoisted to at least the highest level it may be changed (write).
 *
 * 3. If two states change in response to the same events they should be hoisted to the same level.
 *
 * You can hoist the state higher than these rules require, but if you don't hoist the state
 * high enough, it might be difficult or impossible to follow unidirectional data flow.
 *
 */
@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var waterCount by rememberSaveable { mutableIntStateOf(0) }
//    var juiceCount by rememberSaveable { mutableIntStateOf(0) }

    StatelessCounter(
        count = waterCount,
        onIncrement = { waterCount++ },
        modifier = modifier
    )

    /*
     * If `juiceCount` is modified then `StatefulCounter` is recomposed.
     * During recomposition, Compose identifies which functions read `juiceCount` and
     * triggers recomposition of only those functions.
     *
     * When the user taps to increment `juiceCount`, `StatefulCounter` recomposes,
     * and so does the `StatelessCounter` that reads `juiceCount`. But the `StatelessCounter`
     * that reads `waterCount` **IS NOT** recomposed.
     *
     * Reusing the same Stateless Counter
     */
//    StatelessCounter(
//        count = juiceCount,
//        onIncrement = { juiceCount++ }
//    )
}

/**
 * The role of the `StatelessCounter` is to display the count and call a function
 * when you increment the count. To do this, follow the pattern described above and pass the state,
 * count (as a parameter to the composable function), and a lambda (onIncrement),
 * that is called when the state needs to be incremented.
 */
@Composable
fun StatelessCounter(
    count: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(
            onClick = { onIncrement() },
            modifier = Modifier.padding(top = 8.dp),
            enabled = (count < 10)
        ) {
            Text("Add One")
        }
    }

}

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    // Also, in Compose, best practice is to never pass the viewModel instance
    // to other composables. Rather, pass the data that they need
    // And keep the viewModel instance to the root composable
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        StatefulCounter(modifier)

        /*
         * The reason behind doing this step is that -
         * Using mutable objects for this, such as `ArrayList<T>` or `mutableListOf`, won't work.
         * These types won't notify Compose that the items in the list have changed and
         * schedule a recomposition of the UI. You need a different API.
         *
         * You need to create an instance of `MutableList` that is observable by Compose.
         * This structure lets Compose track changes to recompose the UI when items are
         * added or removed from the list.
         *
         * Start by defining our observable `MutableList`.
         * The extension function `toMutableStateList()` is the way to create
         * an observable `MutableList` from an initial mutable or immutable Collection, such as List.
         *
         * IMPORTANT:
         * You can use the `mutableStateListOf` API instead to create the list.
         * However, the way you use it might result in unexpected recomposition and
         * suboptimal UI performance.
         *
         * If you just define the list and then add the tasks in a different operation
         * it would result in duplicated items being added for every recomposition.
         *
         * Don't do this!
         * val list = remember { mutableStateListOf<WellnessTask>() }
         * list.addAll(getWellnessTasks())
         *
         * Instead, create the list with its initial value in a single operation and
         * then pass it to the `remember` function, like this:
         *
         * DO THIS INSTEAD!
         * val list = remember {
         *    mutableStateListOf<WellnessTask>().apply {
         *       addAll(getWellnessTasks())
         *    }
         * }
         *
         * NOTE:
         * Currently, when we remove an item from the list and then rotate the screen
         * The state is not saved during configuration change, and because of that
         * the deleted item re-appears as part of the list.
         *
         * So, as learnt in the previous sections, we can use `rememberSaveable` to make
         * any state be remembered even after configuration change. But since this is a complex
         * type which is not supported by `Bundle`. We'll have to implement
         * ViewModel to take care of this scenario.
         */
        // val list = remember { getWellnessTasks().toMutableStateList() }

        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCheckedTask = { task, checked ->
                // Since we moved the checkedState logic to ViewModel
                // therefore we used it like this
                wellnessViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { task ->
                wellnessViewModel.remove(task)
            }
        )
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    LearningComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            WellnessScreen(modifier)
        }
    }
}

@Composable
fun WaterCounterAppPortrait() {
    LearningComposeTheme {
        Scaffold { padding ->
            HomeScreen(Modifier.padding(padding))
        }
    }
}

@Composable
fun WaterCounterAppLandscape() {
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
                HomeScreen()
            }
        }
    }
}

@Composable
fun WaterCounterApp(windowSize: WindowSizeClass) {
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            WaterCounterAppPortrait()
        }

        WindowWidthSizeClass.Medium -> {
            WaterCounterAppLandscape()
        }

        WindowWidthSizeClass.Expanded -> {
            WaterCounterAppLandscape()
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun WaterCounterPreview() {
//    WaterCounter()
//}