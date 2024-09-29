package com.am.recipe.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.recipe.domain.use_case.GetAreasUseCase
import com.am.recipe.domain.use_case.GetCategoriesUseCase
import com.am.recipe.domain.use_case.GetIngredientsUseCase
import com.am.recipe.domain.model.SearchKeyState
import com.am.recipe.util.Constants.TIMEOUT_MILLIS
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val getAreasUseCase: GetAreasUseCase,
    private val getIngredientsUseCase: GetIngredientsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {

    val areaUiState: StateFlow<SearchKeyState> = getAreasUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = SearchKeyState.Loading
    )

    val ingredientsUiState: StateFlow<SearchKeyState> = getIngredientsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = SearchKeyState.Loading
    )

    val categoryUiState: StateFlow<SearchKeyState> = getCategoriesUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = SearchKeyState.Loading
    )

}