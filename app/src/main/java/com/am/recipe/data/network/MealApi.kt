package com.am.recipe.data.network

import com.am.recipe.data.remote.dto.area.AreaDto
import com.am.recipe.data.remote.dto.category.CategoryDto
import com.am.recipe.data.remote.dto.ingredient.IngredientDto
import com.am.recipe.util.Constants.AREA_ENDPOINT
import com.am.recipe.util.Constants.CATEGORY_ENDPOINT
import com.am.recipe.util.Constants.INGREDIENT_ENDPOINT
import retrofit2.http.GET


interface MealApi {

    @GET(AREA_ENDPOINT)
    suspend fun getAreas(): List<AreaDto>

    @GET(CATEGORY_ENDPOINT)
    suspend fun getCategories(): List<CategoryDto>

    @GET(INGREDIENT_ENDPOINT)
    suspend fun getIngredients(): List<IngredientDto>

}