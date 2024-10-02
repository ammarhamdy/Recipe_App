package com.am.recipe.presentation.ui.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.recipe.domain.model.RecipeState
import com.am.recipe.domain.use_case.GetRecipeUseCase
import com.am.recipe.presentation.model.HTMLGen
import com.am.recipe.presentation.ui.recipe.RecipeDestination.RECIPE_ID
import com.am.recipe.util.Constants.TIMEOUT_MILLIS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel(
    savedStateHandle: SavedStateHandle,
    private val getRecipeUseCase: GetRecipeUseCase
): ViewModel() {

    private val recipeId: String = checkNotNull(savedStateHandle[RECIPE_ID])

    private val _recipeUiState = MutableSharedFlow<Unit>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val recipeUiState = _recipeUiState.flatMapLatest {
        getRecipeUseCase(recipeId)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = RecipeState.Loading
    )

    private val _exportState = MutableStateFlow(false)
    val exportState: StateFlow<Boolean> = _exportState.asStateFlow()

    init {
        reload()
    }

    fun reload(){
        viewModelScope.launch {
            _recipeUiState.emit(Unit)
        }
    }

    fun exportToLocalStorage(){
        viewModelScope.launch(Dispatchers.IO) {
            with(recipeUiState.value){
                if (this is RecipeState.Success){
                    _exportState.value = HTMLGen(this.recipe).exportRecipe()
                    delay(3000L)
                    _exportState.value = false
                }
            }

        }
    }

}