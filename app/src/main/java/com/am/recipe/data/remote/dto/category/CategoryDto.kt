package com.am.recipe.data.remote.dto.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val meals: List<Meal> // list of Categories
)

fun CategoryDto.toCategoriesList(): List<String> =
    this.meals.map { it.strCategory }