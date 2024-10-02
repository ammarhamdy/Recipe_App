package com.am.recipe.domain.model

import com.am.recipe.data.remote.dto.meal.Meal
import com.am.recipe.presentation.model.ErrorType


sealed class MealsState {
    data object Loading: MealsState()
    class Success(val meals: List<Meal>): MealsState()
    class Error(val errorType: ErrorType):  MealsState()
}

fun MealsState.anType() = when(this){
    is MealsState.Error -> AnimationType.ALERT
    MealsState.Loading -> AnimationType.LOADING
    is MealsState.Success -> AnimationType.SUCCESS
}
