package com.am.recipe.domain.use_case

import com.am.recipe.domain.model.MealsState
import com.am.recipe.domain.model.SearchType
import com.am.recipe.domain.repository.MealRepository
import com.am.recipe.presentation.model.ErrorType
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetMealsUseCase(
    private val mealRepository: MealRepository
) {

    operator fun invoke(searchType: SearchType, searchKey: String) = flow {
        try{
            emit(MealsState.Loading)
            kotlinx.coroutines.delay(1500L)
            mealRepository
                .getMeals(searchType, searchKey)
                .meals
                ?.let { emit(MealsState.Success(it)) }
                ?: emit(MealsState.Success(emptyList()))
        } catch (httpE: HttpException) {
            emit(MealsState.Error(ErrorType.HTTP_ERROR))
        } catch (ioE: IOException) {
            emit(MealsState.Error(ErrorType.IO_ERROR))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(MealsState.Error(ErrorType.UNEXPECTED_ERROR))
        }
    }

}