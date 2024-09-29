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


interface MealApi {

    @GET(AREA_ENDPOINT)
    suspend fun getAreas(): List<AreaDto>

    @GET(CATEGORY_ENDPOINT)
    suspend fun getCategories(): List<CategoryDto>

    @GET(INGREDIENT_ENDPOINT)
    suspend fun getIngredients(): List<IngredientDto>

    /*
    * filterBy could be (i=ingredient, a=area, c=category)
    * */
    @GET("$FILTER_ENDPOINT{filterBy}")
    suspend fun getMeals(
        @Path("filterBy") filterBy: String
    ): List<MealDto>

    @GET("$MEAL_DETAILS_ENDPOINT{mealId}")
    suspend fun getMealDetails(
        @Path("mealId") mealId: String
    ): List<MealDetailsDto>

}