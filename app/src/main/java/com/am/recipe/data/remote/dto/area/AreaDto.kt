package com.am.recipe.data.remote.dto.area


data class AreaDto(
    val meals: List<Meal> // list of areas
)

fun AreaDto.toAreasList(): List<String> =
    this.meals.map { it.strArea }