package com.am.recipe.domain.model

import com.am.recipe.presentation.model.ErrorType

sealed class RecipeState {
    data object Loading: RecipeState()
    class Success(val recipe: Recipe): RecipeState()
    class Error(val errorType: ErrorType): RecipeState()
}