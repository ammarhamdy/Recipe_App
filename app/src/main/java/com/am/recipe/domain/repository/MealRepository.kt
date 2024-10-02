package com.am.recipe.domain.repository

import com.am.recipe.data.remote.dto.area.AreaDto
import com.am.recipe.data.remote.dto.category.CategoryDto
import com.am.recipe.data.remote.dto.ingredient.IngredientDto
import com.am.recipe.data.remote.dto.meal.MealDto
import com.am.recipe.data.remote.dto.mealDetails.MealDetailsDto
import com.am.recipe.domain.model.SearchType

interface MealRepository {

    suspend fun getAreas(): AreaDto

    suspend fun getCategories(): CategoryDto

    suspend fun getIngredients(): IngredientDto

    suspend fun getMeals(searchType: SearchType, searchKey: String): MealDto

    suspend fun getMealDetails(mealId: String): MealDetailsDto

}