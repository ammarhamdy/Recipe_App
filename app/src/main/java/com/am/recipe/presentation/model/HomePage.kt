package com.am.recipe.presentation.model

import androidx.compose.runtime.Immutable
import com.am.recipe.domain.model.SearchType

@Immutable
enum class HomePage(val bgIcon: BgIcon) {
    AREA(BgIcon.AREA),
    INGREDIENT(BgIcon.INGREDIENT),
    CATEGORY(BgIcon.CATEGORY);

    fun toSearchType() = when(this){
        AREA -> SearchType.BY_AREA
        INGREDIENT -> SearchType.BY_INGREDIENT
        CATEGORY -> SearchType.BY_CATEGORY
    }
}