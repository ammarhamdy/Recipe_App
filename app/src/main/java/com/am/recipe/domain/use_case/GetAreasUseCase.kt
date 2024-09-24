package com.am.recipe.domain.use_case

import com.am.recipe.data.remote.dto.area.toAreasList
import com.am.recipe.domain.repository.MealRepository
import kotlinx.coroutines.flow.flow

class GetAreasUseCase(
    private val mealRepository: MealRepository
){

    operator fun invoke() = flow {
        val areas = mealRepository
            .getAreas()
            .flatMap { it.toAreasList() }
        emit(areas)
    }

}