package com.am.recipe.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.am.recipe.domain.repository.MealRepository
import com.am.recipe.domain.use_case.GetAreasUseCase
import com.am.recipe.domain.use_case.GetCategoriesUseCase
import com.am.recipe.domain.use_case.GetIngredientsUseCase

class HomeViewModel(
    private val getAreasUseCase: GetAreasUseCase,
    private val getIngredientsUseCase: GetIngredientsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {



}