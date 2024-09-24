package com.am.recipe.presentation.ui.common

import com.am.recipe.data.remote.dto.meal.MealDto

sealed class MealResponseState {
    data object Loading: MealResponseState()
    class Success(val meals: List<MealDto>): MealResponseState()
    class Error(val errorType: ErrorType):  MealResponseState()
}

enum class ErrorType { UNEXPECTED_ERROR, CONNECTION_ERROR }