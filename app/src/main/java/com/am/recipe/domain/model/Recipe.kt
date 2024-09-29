package com.am.recipe.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Recipe(
    val title: String,
    val area: String,
    val category: String,
    val thumb: String,
    val description: String,
    val ingredientMeasureList: List<String>,
    val dateModified: String?,
    val youtubeLink: String?,
    val sourceLink: String?
) {
    companion object {
        val emptyRecipe = Recipe("", "", "", "", "", emptyList(), null, null, null)
    }
}

