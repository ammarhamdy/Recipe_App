package com.am.recipe.presentation.ui.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.recipe.domain.use_case.GetRecipeUseCase
import com.am.recipe.presentation.model.HTMLGen
import com.am.recipe.presentation.ui.recipe.RecipeDestination.RECIPE_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(
    savedStateHandle: SavedStateHandle,
    private val getRecipeUseCase: GetRecipeUseCase
): ViewModel() {

//    val recipeId = savedStateHandle[RECIPE_ID]



    private val _exportState = MutableStateFlow(true)
    val exportState: StateFlow<Boolean> = _exportState.asStateFlow()



    fun exportToLocalStorage(){
        viewModelScope.launch(Dispatchers.IO) {
            _exportState.value = false
        }
    }

}