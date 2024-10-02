package com.am.recipe.domain.model

import com.am.recipe.presentation.model.ErrorType

sealed class SearchKeyState {
    data object Loading: SearchKeyState()
    class Success(val keys: List<String>) : SearchKeyState()
    class SuccessWithGroups(val keys: Map<Char, List<String>>) : SearchKeyState()
    class Error(val errorType: ErrorType): SearchKeyState()
}

fun SearchKeyState.anType() = when(this){
    is SearchKeyState.Error -> AnimationType.ALERT
    SearchKeyState.Loading -> AnimationType.LOADING
    is SearchKeyState.Success -> AnimationType.SUCCESS
    is SearchKeyState.SuccessWithGroups -> AnimationType.SUCCESS
}