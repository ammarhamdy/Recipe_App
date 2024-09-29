package com.am.recipe.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.am.recipe.presentation.ui.home.HomeDestination
import com.am.recipe.presentation.ui.home.HomeScreen
import com.am.recipe.presentation.ui.meal_list.MealsDestination
import com.am.recipe.presentation.ui.meal_list.MealsScreen
import com.am.recipe.presentation.ui.recipe.RecipeDestination

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
            val navigateToMealsScreen = { searchKey: String ->
                navController.navigate("${MealsDestination.route}/$searchKey")
            }
            HomeScreen(navigateToMealList = navigateToMealsScreen)
        }

        composable(
            route = MealsDestination.routeWithArg,
            arguments = listOf(navArgument(MealsDestination.FILTER_BY){ type = NavType.StringType })
        ){
            MealsScreen {

            }
        }

        composable(
            route = RecipeDestination.route,
            arguments = listOf(navArgument(RecipeDestination.RECIPE_ID){ NavType.StringType })
        ){

        }

    }
}
