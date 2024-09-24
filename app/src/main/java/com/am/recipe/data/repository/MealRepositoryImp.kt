package com.am.recipe.data.repository

import com.am.recipe.data.network.MealApi
import com.am.recipe.data.remote.dto.area.AreaDto
import com.am.recipe.data.remote.dto.area.toAreasList
import com.am.recipe.data.remote.dto.category.CategoryDto
import com.am.recipe.data.remote.dto.ingredient.IngredientDto
import com.am.recipe.domain.repository.MealRepository

class MealRepositoryImp(
    private val mealApi: MealApi
): MealRepository {

    override suspend fun getAreas(): List<AreaDto> =
        mealApi
            .getAreas()

    override suspend fun getCategories(): List<CategoryDto> =
        mealApi.getCategories()

    override suspend fun getIngredients(): List<IngredientDto> =
        mealApi.getIngredients()

}