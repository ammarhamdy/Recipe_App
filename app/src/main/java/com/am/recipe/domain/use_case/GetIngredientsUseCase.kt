package com.am.recipe.domain.use_case

import com.am.recipe.data.remote.dto.ingredient.toIngredientsList
import com.am.recipe.domain.repository.MealRepository
import kotlinx.coroutines.flow.flow

class GetIngredientsUseCase(
    private val mealRepository: MealRepository
) {

    operator fun invoke() = flow {
        val ingredients = mealRepository
            .getIngredients()
            .flatMap { it.toIngredientsList() }
        emit(ingredients)
    }

}