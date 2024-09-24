package com.am.recipe.presentation.ui.meal_list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.am.recipe.R
import com.am.recipe.presentation.ui.navigation.NavigationDestination

object MealListDestination: NavigationDestination {
    override val route = "meal_list"
    override val titleRes = R.string.meal_list_title

}


@Composable
fun MealListScreen(modifier: Modifier = Modifier) {

}