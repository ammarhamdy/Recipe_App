package com.am.recipe.presentation.ui.common


sealed class MealResponseState {
    data object Loading: MealResponseState()
    class Success(val items: List<String>): MealResponseState()
    class Error(val errorType: ErrorType):  MealResponseState()
}

