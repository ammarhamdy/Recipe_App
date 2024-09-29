package com.am.recipe.data.remote.dto.meal

import androidx.compose.runtime.Immutable


@Immutable
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)