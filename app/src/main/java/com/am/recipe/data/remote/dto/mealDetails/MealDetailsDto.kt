package com.am.recipe.data.remote.dto.mealDetails

import kotlinx.serialization.Serializable

@Serializable
data class MealDetailsDto(
    val meals: List<MealDetails>
)

