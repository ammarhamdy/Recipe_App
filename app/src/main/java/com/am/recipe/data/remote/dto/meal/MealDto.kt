package com.am.recipe.data.remote.dto.meal

import kotlinx.serialization.Serializable


@Serializable
data class MealDto(
    val meals: List<Meal>?
)
