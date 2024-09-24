package com.am.recipe.domain.use_case

import com.am.recipe.data.remote.dto.category.toCategoriesList
import com.am.recipe.domain.repository.MealRepository
import kotlinx.coroutines.flow.flow

class GetCategoriesUseCase(
    private val mealRepository: MealRepository
) {

    operator fun invoke() = flow {
        val categories = mealRepository
            .getCategories()
            .flatMap { it.toCategoriesList() }
        emit(categories)
    }

}