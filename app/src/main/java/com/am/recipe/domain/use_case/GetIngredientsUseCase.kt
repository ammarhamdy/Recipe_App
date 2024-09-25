package com.am.recipe.domain.use_case

import com.am.recipe.data.remote.dto.ingredient.toIngredientsList
import com.am.recipe.domain.repository.MealRepository
import com.am.recipe.presentation.ui.common.ErrorType
import com.am.recipe.presentation.ui.common.MealResponseState
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetIngredientsUseCase(
    private val mealRepository: MealRepository
) {

    operator fun invoke() = flow {
        try {
            emit(
                MealResponseState.Success(
                    mealRepository
                        .getIngredients()
                        .flatMap { it.toIngredientsList() }
                )
            )
        } catch (httpE: HttpException) {
            emit(MealResponseState.Error(ErrorType.HTTP_ERROR))
        } catch (ioE: IOException) {
            emit(MealResponseState.Error(ErrorType.IO_ERROR))
        } catch (e: Exception) {
            emit(MealResponseState.Error(ErrorType.UNEXPECTED_ERROR))
        }
    }

}