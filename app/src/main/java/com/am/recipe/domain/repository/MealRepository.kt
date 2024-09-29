package com.am.recipe.domain.repository

import com.am.recipe.data.remote.dto.area.AreaDto
import com.am.recipe.data.remote.dto.category.CategoryDto
import com.am.recipe.data.remote.dto.ingredient.IngredientDto
import com.am.recipe.data.remote.dto.meal.MealDto
import com.am.recipe.data.remote.dto.mealDetails.MealDetailsDto

interface MealRepository {

    suspend fun getAreas(): List<AreaDto>

    suspend fun getCategories(): List<CategoryDto>

    suspend fun getIngredients(): List<IngredientDto>

    suspend fun getMeals(filterBy: String): List<MealDto>

    suspend fun getMealDetails(mealId: String): List<MealDetailsDto>

}