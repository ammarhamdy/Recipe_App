package com.am.recipe.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
enum class ErrorType { UNEXPECTED_ERROR, IO_ERROR, HTTP_ERROR }