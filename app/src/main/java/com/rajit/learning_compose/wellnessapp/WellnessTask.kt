package com.rajit.learning_compose.wellnessapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

//data class WellnessTask(
//    val id: Int,
//    val label: String,
//    // We didn't go this way because Compose is unable to track changes to this field
//    // It needs to be a mutableState variable for Compose to track changes to it
//    // var checked: Boolean = false
//)

/**
 * Since Compose is unable to track changes to WellnessTask Variables
 * We had to refactor it a bit to include the mutableState method for
 * the Compose to track the changes to the field appropriately
 *
 * Also, refactoring the data class to simple class was because of the
 * usage of Kotlin delegate properties for the code to be simpler and readable
 */
class WellnessTask(
    val id: Int,
    val label: String,
    initialChecked: Boolean = false
) {
    var checked by mutableStateOf(initialChecked)
}
