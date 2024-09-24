package com.am.recipe.domain.repository

import com.am.recipe.data.remote.dto.area.AreaDto
import com.am.recipe.data.remote.dto.category.CategoryDto
import com.am.recipe.data.remote.dto.ingredient.IngredientDto

interface MealRepository {

    suspend fun getAreas(): List<AreaDto>

    suspend fun getCategories(): List<CategoryDto>

    suspend fun getIngredients(): List<IngredientDto>

}