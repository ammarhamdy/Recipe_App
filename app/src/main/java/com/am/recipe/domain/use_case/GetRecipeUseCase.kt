package com.am.recipe.domain.use_case

import com.am.recipe.data.remote.dto.mealDetails.toRecipe
import com.am.recipe.domain.model.Recipe
import com.am.recipe.domain.model.RecipeState
import com.am.recipe.domain.repository.MealRepository
import com.am.recipe.presentation.model.ErrorType
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetRecipeUseCase(
    private val mealRepository: MealRepository
) {

    operator fun invoke(mealId: String) = flow {
        try {
            emit(RecipeState.Loading)
            kotlinx.coroutines.delay(1500L)
            emit(
                RecipeState.Success(
                    mealRepository
                        .getMealDetails(mealId)
                        .meals
                        .firstOrNull()
                        ?.toRecipe()
                        ?: Recipe.emptyRecipe
                )
            )
        } catch (httpE: HttpException) {
            emit(RecipeState.Error(ErrorType.HTTP_ERROR))
        } catch (ioE: IOException) {
            emit(RecipeState.Error(ErrorType.IO_ERROR))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(RecipeState.Error(ErrorType.UNEXPECTED_ERROR))
        }
    }

}