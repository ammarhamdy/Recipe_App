package com.am.recipe.data.remote.dto.mealDetails

import com.am.recipe.domain.model.Recipe
import kotlinx.serialization.Serializable

@Serializable
data class MealDetails(
    val idMeal: String, // id
    val strMeal: String, // title
    val strArea: String, // area
    val strCategory: String, // category
    val strMealThumb: String, // image
    val strInstructions: String, // description
    val strYoutube: String?, // youtube link
    val strSource: String?, // link to the source of recipe

    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    val strIngredient16: String?,
    val strIngredient17: String?,
    val strIngredient18: String?,
    val strIngredient19: String?,
    val strIngredient20: String?,

    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,
    val strMeasure16: String?,
    val strMeasure17: String?,
    val strMeasure18: String?,
    val strMeasure19: String?,
    val strMeasure20: String?,

    val strTags: String?,
    val dateModified: String?,
    val strImageSource: String?,
    val strCreativeCommonsConfirmed: String?,
    val strDrinkAlternate: String?,
)

fun MealDetails.toRecipe() =
    Recipe(
        strMeal,
        strArea,
        strCategory,
        strMealThumb,
        strInstructions,
        listOfNotNull(
            if (isValid(strIngredient1) && isValid(strMeasure1)) "$strIngredient1#$strMeasure1" else null,
            if (isValid(strIngredient2) && isValid(strMeasure2)) "$strIngredient2#$strMeasure2" else null,
            if (isValid(strIngredient3) && isValid(strMeasure3)) "$strIngredient3#$strMeasure3" else null,
            if (isValid(strIngredient4) && isValid(strMeasure4)) "$strIngredient4#$strMeasure4" else null,
            if (isValid(strIngredient5) && isValid(strMeasure5)) "$strIngredient5#$strMeasure5" else null,
            if (isValid(strIngredient6) && isValid(strMeasure6)) "$strIngredient6#$strMeasure6" else null,
            if (isValid(strIngredient7) && isValid(strMeasure7)) "$strIngredient7#$strMeasure7" else null,
            if (isValid(strIngredient8) && isValid(strMeasure8)) "$strIngredient8#$strMeasure8" else null,
            if (isValid(strIngredient9) && isValid(strMeasure9)) "$strIngredient9#$strMeasure9" else null,
            if (isValid(strIngredient10) && isValid(strMeasure10)) "$strIngredient10#$strMeasure10" else null,
            if (isValid(strIngredient11) && isValid(strMeasure11)) "$strIngredient11#$strMeasure11" else null,
            if (isValid(strIngredient12) && isValid(strMeasure12)) "$strIngredient12#$strMeasure12" else null,
            if (isValid(strIngredient13) && isValid(strMeasure13)) "$strIngredient13#$strMeasure13" else null,
            if (isValid(strIngredient14) && isValid(strMeasure14)) "$strIngredient14#$strMeasure14" else null,
            if (isValid(strIngredient15) && isValid(strMeasure15)) "$strIngredient15#$strMeasure15" else null,
            if (isValid(strIngredient16) && isValid(strMeasure16)) "$strIngredient16#$strMeasure16" else null,
            if (isValid(strIngredient17) && isValid(strMeasure17)) "$strIngredient17#$strMeasure17" else null,
            if (isValid(strIngredient18) && isValid(strMeasure18)) "$strIngredient18#$strMeasure18" else null,
            if (isValid(strIngredient19) && isValid(strMeasure19)) "$strIngredient19#$strMeasure19" else null,
            if (isValid(strIngredient20) && isValid(strMeasure20)) "$strIngredient20#$strMeasure20" else null,
        ),
        dateModified,
        strYoutube,
        strSource,
    )


private fun isValid(ingredient: String?) =
    ingredient?.isNotBlank() == true