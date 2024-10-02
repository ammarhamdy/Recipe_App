package com.am.recipe.domain.model

import androidx.compose.runtime.Immutable

@Immutable
enum class SearchType(val value: Char) {
    BY_AREA('a'),
    BY_INGREDIENT('i'),
    BY_CATEGORY('c')
}

fun searchTypeOf(value: Char) = when(value){
    SearchType.BY_AREA.value -> SearchType.BY_AREA
    SearchType.BY_INGREDIENT.value -> SearchType.BY_INGREDIENT
    SearchType.BY_CATEGORY.value -> SearchType.BY_CATEGORY
    else -> throw IllegalArgumentException()
}