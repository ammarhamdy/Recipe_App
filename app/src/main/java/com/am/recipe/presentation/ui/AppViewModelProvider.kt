package com.am.recipe.presentation.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.am.recipe.MealApplication
import com.am.recipe.domain.use_case.GetAreasUseCase
import com.am.recipe.domain.use_case.GetCategoriesUseCase
import com.am.recipe.domain.use_case.GetIngredientsUseCase
import com.am.recipe.domain.use_case.GetMealsUseCase
import com.am.recipe.domain.use_case.GetRecipeUseCase
import com.am.recipe.presentation.ui.home.HomeViewModel
import com.am.recipe.presentation.ui.meal_list.MealsViewModel
import com.am.recipe.presentation.ui.recipe.RecipeViewModel

object AppViewModelProvider {

    val viewModelFactory = viewModelFactory {

        // initializer for home view model.
        initializer {
            val repo = mealApplication().application.mealsRepository
            HomeViewModel(
                GetAreasUseCase(repo),
                GetIngredientsUseCase(repo),
                GetCategoriesUseCase(repo)
            )
        }

        // initializer for meals view model
        initializer {
            val repo = mealApplication().application.mealsRepository
            MealsViewModel(this.createSavedStateHandle(), GetMealsUseCase(repo))
        }

        // initializer for meals view model
        initializer {
            val repo = mealApplication().application.mealsRepository
            RecipeViewModel(this.createSavedStateHandle(), GetRecipeUseCase(repo))
        }


    }

}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [MealApplication].
 */
fun CreationExtras.mealApplication(): MealApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MealApplication)