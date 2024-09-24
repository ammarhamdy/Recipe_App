package com.am.recipe.domain.model

import com.am.recipe.domain.repository.MealRepository

interface AppContainer {

    val mealsRepository: MealRepository

}