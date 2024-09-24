package com.am.recipe.data.remote.dto.ingredient


data class IngredientDto(
    val meals: List<Meal> // list of Ingredients
)

fun IngredientDto.toIngredientsList(): List<String> =
    this.meals.map { it.strIngredient }