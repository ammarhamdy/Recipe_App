package com.am.recipe.data.repository

import com.am.recipe.data.remote.MealApi
import com.am.recipe.data.remote.dto.area.AreaDto
import com.am.recipe.data.remote.dto.category.CategoryDto
import com.am.recipe.data.remote.dto.ingredient.IngredientDto
import com.am.recipe.data.remote.dto.meal.MealDto
import com.am.recipe.data.remote.dto.mealDetails.MealDetailsDto
import com.am.recipe.domain.repository.MealRepository

class MealRepositoryImp(
    private val mealApi: MealApi
): MealRepository {

    override suspend fun getAreas(): List<AreaDto> =
        mealApi.getAreas()

    override suspend fun getCategories(): List<CategoryDto> =
        mealApi.getCategories()

    override suspend fun getIngredients(): List<IngredientDto> =
        mealApi.getIngredients()

    override suspend fun getMeals(filterBy: String): List<MealDto> =
        mealApi.getMeals(filterBy)

    override suspend fun getMealDetails(mealId: String): List<MealDetailsDto> =
        mealApi.getMealDetails(mealId)

}