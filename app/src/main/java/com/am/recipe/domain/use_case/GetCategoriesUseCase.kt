package com.am.recipe.domain.use_case

import com.am.recipe.data.remote.dto.category.toCategoriesList
import com.am.recipe.domain.repository.MealRepository
import com.am.recipe.presentation.model.ErrorType
import com.am.recipe.domain.model.SearchKeyState
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetCategoriesUseCase(
    private val mealRepository: MealRepository
) {

    operator fun invoke() = flow {
        try {
            emit(SearchKeyState.Loading)
            emit(
                SearchKeyState.Success(
                    mealRepository
                        .getCategories()
                        .meals
                        .map { it.strCategory }
                        .sorted()
                )
            )
        } catch (httpE: HttpException) {
            emit(SearchKeyState.Error(ErrorType.HTTP_ERROR))
        } catch (ioE: IOException) {
            emit(SearchKeyState.Error(ErrorType.IO_ERROR))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(SearchKeyState.Error(ErrorType.UNEXPECTED_ERROR))
        }
    }

}