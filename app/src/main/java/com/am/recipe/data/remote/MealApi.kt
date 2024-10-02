package com.am.recipe.data.remote

import com.am.recipe.data.remote.dto.area.AreaDto
import com.am.recipe.data.remote.dto.category.CategoryDto
import com.am.recipe.data.remote.dto.ingredient.IngredientDto
import com.am.recipe.data.remote.dto.meal.MealDto
import com.am.recipe.data.remote.dto.mealDetails.MealDetailsDto
import com.am.recipe.util.Constants.AREA_ENDPOINT
import com.am.recipe.util.Constants.CATEGORY_ENDPOINT
import com.am.recipe.util.Constants.FILTER_ENDPOINT
import com.am.recipe.util.Constants.INGREDIENT_ENDPOINT
import com.am.recipe.util.Constants.MEAL_DETAILS_ENDPOINT
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MealApi {

    @GET(AREA_ENDPOINT)
    suspend fun getAreas(): AreaDto

    @GET(CATEGORY_ENDPOINT)
    suspend fun getCategories(): CategoryDto

    @GET(INGREDIENT_ENDPOINT)
    suspend fun getIngredients(): IngredientDto

    //.. filters ..\\
    @GET(FILTER_ENDPOINT)
    suspend fun getMealsByArea(
        @Query("a") area: String
    ): MealDto

    @GET(FILTER_ENDPOINT)
    suspend fun getMealsByCategory(
        @Query("c") category: String
    ): MealDto

    @GET(FILTER_ENDPOINT)
    suspend fun getMealsByIngredient(
        @Query("i") ingredient: String
    ): MealDto

    //.. single item ..\\
    @GET(MEAL_DETAILS_ENDPOINT)
    suspend fun getMealDetails(
        @Query("i") mealId: String
    ): MealDetailsDto

}