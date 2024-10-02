package com.am.recipe.presentation.ui.meal_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.recipe.domain.model.MealsState
import com.am.recipe.domain.model.searchTypeOf
import com.am.recipe.domain.use_case.GetMealsUseCase
import com.am.recipe.presentation.ui.meal_list.MealsDestination.FILTER_BY
import com.am.recipe.util.Constants.TIMEOUT_MILLIS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MealsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMealsUseCase: GetMealsUseCase
): ViewModel() {

    private val searchValue: String = checkNotNull(savedStateHandle[FILTER_BY])

    private val _mealsUiState = MutableSharedFlow<Unit>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val mealsUiState: StateFlow<MealsState> = _mealsUiState
        .flatMapLatest{
            getMealsUseCase(
                searchTypeOf(searchValue.first()),
                searchValue.substring(1)
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = MealsState.Loading,
        )

    init {
        reload()
    }

    fun reload(){
        viewModelScope.launch {
            _mealsUiState.emit(Unit)
        }
    }

}