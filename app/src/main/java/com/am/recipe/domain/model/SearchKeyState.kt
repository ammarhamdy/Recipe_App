package com.am.recipe.domain.model

import com.am.recipe.presentation.model.ErrorType

sealed class SearchKeyState {
    data object Loading: SearchKeyState()
    class Success(val keys: List<String>) : SearchKeyState()
    class Error(val errorType: ErrorType): SearchKeyState()
}