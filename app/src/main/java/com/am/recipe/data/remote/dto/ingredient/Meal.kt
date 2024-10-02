package com.am.recipe.data.remote.dto.ingredient

import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    val idIngredient: String,
    val strIngredient: String,
    val strDescription: String?,
    val strType: String?
)