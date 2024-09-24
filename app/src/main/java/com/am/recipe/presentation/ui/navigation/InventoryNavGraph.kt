package com.am.recipe.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.am.recipe.presentation.ui.home.HomeDestination
import com.am.recipe.presentation.ui.home.HomeScreen

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun MealNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ){

        composable(route = HomeDestination.route){
            //HomeScreen()
        }

    }
}
