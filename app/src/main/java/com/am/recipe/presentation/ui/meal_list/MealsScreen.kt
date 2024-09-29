package com.am.recipe.presentation.ui.meal_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.am.recipe.R
import com.am.recipe.data.remote.dto.meal.Meal
import com.am.recipe.domain.model.MealsState
import com.am.recipe.presentation.model.BgIcon
import com.am.recipe.presentation.model.ZigZagRec
import com.am.recipe.presentation.ui.common.GlassyLayer
import com.am.recipe.presentation.ui.common.MultiIconsBackGround
import com.am.recipe.presentation.ui.common.RecipeTopAppBar
import com.am.recipe.presentation.ui.home.ErrorMessCard
import com.am.recipe.presentation.ui.navigation.NavigationDestination
import com.am.recipe.presentation.ui.theme.RecipeTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

object MealsDestination: NavigationDestination {
    override val route = "meal_list"
    override val titleRes = R.string.meal_list_title
    const val FILTER_BY = "filterBy"
    val routeWithArg = "$route/{$FILTER_BY}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealsScreen(
    navigateToRecipe: (String) -> Unit,
//    viewModel: MealsViewModel = viewModel(factory = AppViewModelProvider.viewModelFactory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = MealsState.Success(fakeMeals)
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RecipeTopAppBar(
                stringResource(MealsDestination.titleRes),
                true,
                scrollBehavior
            )
        }
    ) {
        MealsBody(
            uiState,
            navigateToRecipe,
            Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        )
    }
}

@Composable
fun MealsBody(
    uiState: MealsState,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier){
        MultiIconsBackGround(
            color = MaterialTheme.colorScheme.onSurface,
            errorColor = MaterialTheme.colorScheme.tertiary,
            bgIcons = listOf(BgIcon.EGG, BgIcon.PIZZA, BgIcon.ORANGE, BgIcon.COOKIE)
                .shuffled()
                .subList(0, 2)
                .toImmutableList(),
            Modifier.fillMaxSize(),
            uiState is MealsState.Loading,
            uiState is MealsState.Error,
        )
        if (uiState is MealsState.Error)
            ErrorMessCard(uiState.errorType, Modifier.fillMaxSize())
        else if (uiState is MealsState.Success)
            MealsList(
                uiState.meals.toImmutableList(),
                onItemClicked
            )
    }
}

@Composable
fun MealsList(
    meals: ImmutableList<Meal>,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
    ) {
        val cardModifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        itemsIndexed(meals, key = { i, _ -> i} ){ _, meal ->
            MealItemCard(meal, { onItemClicked(meal.idMeal) }, cardModifier)
        }
    }
}

@Composable
fun MealItemCard(
    meal: Meal,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.Center, modifier = modifier){
        GlassyLayer(MaterialTheme.colorScheme.primary, Modifier.matchParentSize())
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Card(
                onClick = onClick,
                shape = ZigZagRec(),
                modifier = modifier
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(meal.strMealThumb)
                        .crossfade(true)
                        .build(),
                    error = painterResource(R.drawable.no_meals_24px),
                    placeholder = painterResource(R.drawable.filled_skillet_24),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                text = meal.strMeal,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun MealItemCardPreview() {
    RecipeTheme {
        Surface {
            MealsList(
                fakeMeals,
                {}
            )
        }
    }
}

@Preview
@Composable
private fun MealsScreenPreview() {
    RecipeTheme(darkTheme = true) {
        Surface {
            MealsScreen({})
        }
    }
}

val fakeMeals = listOf(
    Meal("52802", "Fish pie", "https://www.themealdb.com/images/media/meals/ysxwuq1487323065.jpg"),
    Meal("52803", "Fish pie", "https://www.themealdb.com/images/media/meals/ysxwuq1487323065.jpg"),
    Meal("52807", "Fish pie", "https://www.themealdb.com/images/media/meals/ysxwuq1487323065.jpg"),
    Meal("52805", "Fish pie", "https://www.themealdb.com/images/media/meals/ysxwuq1487323065.jpg"),
    Meal("52801", "Fish pie", "https://www.themealdb.com/images/media/meals/ysxwuq1487323065.jpg"),
    Meal("52809", "Fish pie", "https://www.themealdb.com/images/media/meals/ysxwuq1487323065.jpg")
).toImmutableList()