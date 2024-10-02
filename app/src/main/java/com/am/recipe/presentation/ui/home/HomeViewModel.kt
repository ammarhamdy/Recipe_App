package com.am.recipe.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.recipe.domain.use_case.GetAreasUseCase
import com.am.recipe.domain.use_case.GetCategoriesUseCase
import com.am.recipe.domain.use_case.GetIngredientsUseCase
import com.am.recipe.domain.model.SearchKeyState
import com.am.recipe.util.Constants.TIMEOUT_MILLIS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAreasUseCase: GetAreasUseCase,
    private val getIngredientsUseCase: GetIngredientsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {

    private val _areaUiState = MutableSharedFlow<Unit>(replay = 1)
    @OptIn(ExperimentalCoroutinesApi::class)
    val areaUiState: StateFlow<SearchKeyState> = _areaUiState
        .flatMapLatest { getAreasUseCase() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = SearchKeyState.Loading
        )

    private val _ingredientsUiState = MutableSharedFlow<Unit>(replay = 1)
    @OptIn(ExperimentalCoroutinesApi::class)
    val ingredientsUiState: StateFlow<SearchKeyState> = _ingredientsUiState
        .flatMapLatest { getIngredientsUseCase() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = SearchKeyState.Loading
        )

    private val _categoryUiState = MutableSharedFlow<Unit>(replay = 1)
    @OptIn(ExperimentalCoroutinesApi::class)
    val categoryUiState: StateFlow<SearchKeyState> = _categoryUiState
        .flatMapLatest { getCategoriesUseCase() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = SearchKeyState.Loading
        )

    init {
        reloadAreas()
        reloadIngredients()
        reloadCategories()
    }

    fun reloadAreas(){
        viewModelScope.launch {
            _areaUiState.emit(Unit)
        }
    }

    fun reloadIngredients(){
        viewModelScope.launch {
            _ingredientsUiState.emit(Unit)
        }
    }

    fun reloadCategories(){
        viewModelScope.launch {
            _categoryUiState.emit(Unit)
        }
    }

}