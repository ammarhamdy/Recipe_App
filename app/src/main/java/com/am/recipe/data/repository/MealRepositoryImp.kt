package com.am.recipe.data.repository

import com.am.recipe.data.remote.MealApi
import com.am.recipe.data.remote.dto.area.AreaDto
import com.am.recipe.data.remote.dto.category.CategoryDto
import com.am.recipe.data.remote.dto.ingredient.IngredientDto
import com.am.recipe.data.remote.dto.meal.MealDto
import com.am.recipe.data.remote.dto.mealDetails.MealDetailsDto
import com.am.recipe.domain.model.SearchType
import com.am.recipe.domain.repository.MealRepository

class MealRepositoryImp(
    private val mealApi: MealApi
): MealRepository {

    override suspend fun getAreas(): AreaDto =
        mealApi.getAreas()

    override suspend fun getCategories(): CategoryDto =
        mealApi.getCategories()

    override suspend fun getIngredients(): IngredientDto =
        mealApi.getIngredients()

    override suspend fun getMeals(searchType: SearchType, searchKey: String): MealDto =
        when(searchType){
            SearchType.BY_AREA -> mealApi.getMealsByArea(searchKey)
            SearchType.BY_INGREDIENT -> mealApi.getMealsByIngredient(searchKey)
            SearchType.BY_CATEGORY -> mealApi.getMealsByCategory(searchKey)
        }

    override suspend fun getMealDetails(mealId: String): MealDetailsDto =
        mealApi.getMealDetails(mealId)

}