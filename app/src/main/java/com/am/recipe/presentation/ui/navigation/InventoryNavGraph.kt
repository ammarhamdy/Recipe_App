package com.am.recipe.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.am.recipe.presentation.model.BgIcon
import com.am.recipe.presentation.ui.home.HomeDestination
import com.am.recipe.presentation.ui.home.HomeScreen
import com.am.recipe.presentation.ui.meal_list.MealsDestination
import com.am.recipe.presentation.ui.meal_list.MealsScreen
import com.am.recipe.presentation.ui.recipe.RecipeDestination
import com.am.recipe.presentation.ui.recipe.RecipeScreen
import kotlinx.collections.immutable.toImmutableList

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
            HomeScreen(navigateToMealsScreen)
        }

        composable(
            route = MealsDestination.routeWithArg,
            arguments = listOf(navArgument(MealsDestination.FILTER_BY){ type = NavType.StringType })
        ){
            val navigateToRecipeScreen = { recipeId: String ->
                navController.navigate("${RecipeDestination.route}/$recipeId")
            }
            val icons = listOf(BgIcon.EGG, BgIcon.PIZZA, BgIcon.ORANGE, BgIcon.COOKIE)
                .shuffled()
                .subList(0, 2)
                .toImmutableList()
            MealsScreen(icons, navigateToRecipeScreen, navController::navigateUp)
        }

        composable(
            route = RecipeDestination.routeWithArg,
            arguments = listOf(navArgument(RecipeDestination.RECIPE_ID){ NavType.StringType })
        ){
            RecipeScreen(navController::navigateUp)
        }

    }
}
