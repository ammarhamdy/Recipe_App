package com.am.recipe.presentation.ui.meal_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.am.recipe.domain.use_case.GetMealsUseCase

class MealsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMealsUseCase: GetMealsUseCase
): ViewModel() {

//    val s: String = savedStateHandle[FILTER_BY]

}