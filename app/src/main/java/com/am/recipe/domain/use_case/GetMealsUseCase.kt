package com.am.recipe.domain.use_case

import com.am.recipe.domain.model.MealsState
import com.am.recipe.domain.repository.MealRepository
import com.am.recipe.presentation.model.ErrorType
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetMealsUseCase(
    private val mealRepository: MealRepository
) {

    operator fun invoke(filterBy: String) = flow {
        try{
            emit(
                MealsState.Success(
                    mealRepository
                        .getMeals(filterBy)
                        .flatMap { it.meals }
                )
            )
        } catch (httpE: HttpException) {
            emit(MealsState.Error(ErrorType.HTTP_ERROR))
        } catch (ioE: IOException) {
            emit(MealsState.Error(ErrorType.IO_ERROR))
        } catch (e: Exception) {
            emit(MealsState.Error(ErrorType.UNEXPECTED_ERROR))
        }
    }

}