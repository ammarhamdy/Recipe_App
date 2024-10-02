package com.am.recipe.presentation.ui.meal_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.am.recipe.R
import com.am.recipe.data.remote.dto.meal.Meal
import com.am.recipe.domain.model.MealsState
import com.am.recipe.domain.model.anType
import com.am.recipe.presentation.model.BgIcon
import com.am.recipe.presentation.model.ZigZagRec
import com.am.recipe.presentation.ui.AppViewModelProvider
import com.am.recipe.presentation.ui.common.AlertMessCard
import com.am.recipe.presentation.ui.common.ErrorMessCard
import com.am.recipe.presentation.ui.common.GlassyLayer
import com.am.recipe.presentation.ui.common.MultiIconsBackGround
import com.am.recipe.presentation.ui.common.RecipeTopAppBar
import com.am.recipe.presentation.ui.common.WaveMultiIconsBackGround
import com.am.recipe.presentation.ui.navigation.NavigationDestination
import com.am.recipe.presentation.ui.theme.RecipeTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay

object MealsDestination: NavigationDestination {
    override val route = "meal_list"
    override val titleRes = R.string.meal_list_title
    const val FILTER_BY = "filterBy"
    val routeWithArg = "$route/{$FILTER_BY}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealsScreen(
    icons: ImmutableList<BgIcon>,
    navigateToRecipe: (String) -> Unit,
    navigateUp: () -> Unit,
    viewModel: MealsViewModel = viewModel(factory = AppViewModelProvider.viewModelFactory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pullToRefresh = rememberPullToRefreshState()
    val uiState = viewModel.mealsUiState.collectAsState()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RecipeTopAppBar(
                stringResource(MealsDestination.titleRes),
                true,
                scrollBehavior,
                navigateUp = navigateUp
            )
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .nestedScroll(pullToRefresh.nestedScrollConnection)
        ){
            MealsBody(
                uiState.value,
                icons,
                navigateToRecipe,
                Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding())
            )
            PullToRefreshContainer(
                state = pullToRefresh,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(it)
            )
        }
        if(pullToRefresh.isRefreshing)
            LaunchedEffect(true) { viewModel.reload() }
        when(uiState.value){
            is MealsState.Error -> LaunchedEffect(true) {
                delay(500L)
                pullToRefresh.endRefresh()
            }
            MealsState.Loading -> Unit
            is MealsState.Success -> LaunchedEffect(true) {
                delay(300L)
                pullToRefresh.endRefresh()
            }
        }
    }
}

@Composable
fun MealsBody(
    uiState: MealsState,
    icons: ImmutableList<BgIcon>,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isEmptyList = remember { mutableStateOf(false) }
    val parentSize = remember { mutableIntStateOf(0) }
    Box(
        modifier.onGloballyPositioned { parentSize.intValue = it.size.height }
    ){
        WaveMultiIconsBackGround(
            color = MaterialTheme.colorScheme.onSurface,
            errorColor = if (isEmptyList.value)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.tertiary,
            parentSize.intValue,
            bgIcons = icons,
            uiState.anType()
        )
        if (uiState is MealsState.Error)
            ErrorMessCard(
                uiState.errorType,
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
        else if (uiState is MealsState.Success)
            if (uiState.meals.isEmpty()){
                AlertMessCard(R.string.sorry_can_not_find_recipes, Modifier.fillMaxSize())
                isEmptyList.value = true
            }
            else
                MealsList(uiState.meals.toImmutableList(), onItemClicked)
    }
}

@Composable
fun MealsList(
    meals: ImmutableList<Meal>,
    onItemClicked: (String) -> Unit,
) {
    val cardModifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.small_padding))
    ) {
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
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Card(
                elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation)),
                onClick = onClick,
                shape = ZigZagRec(),
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.tiny_padding))
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
            Card(
                elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation)),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier
                    .padding(start = dimensionResource(R.dimen.small_padding))
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(.6f)
            ) {
                Text(
                    text = meal.strMeal,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MealItemCardPreview() {
    RecipeTheme(darkTheme = true) {
        Surface {
            val fakeMeals = listOf(
                Meal("", "Fish pie", "https://www.themealdb.com/images/media/meals/ysxwuq1487323065.jpg"),
                Meal("", "Fish pie", "https://www.themealdb.com/images/media/meals/ysxwuq1487323065.jpg"),
                Meal("", "Fish pie", "https://www.themealdb.com/images/media/meals/ysxwuq1487323065.jpg"),
                Meal("", "Fish pie", "https://www.themealdb.com/images/media/meals/ysxwuq1487323065.jpg"),
                Meal("", "Fish pie", "https://www.themealdb.com/images/media/meals/ysxwuq1487323065.jpg"),
                Meal("", "Fish pie", "https://www.themealdb.com/images/media/meals/ysxwuq1487323065.jpg")
            ).toImmutableList()
            MealsList(fakeMeals) {}
        }
    }
}

@Preview
@Composable
private fun MealsScreenPreview() {
    RecipeTheme(darkTheme = true) {
        val icons = listOf(BgIcon.EGG, BgIcon.PIZZA, BgIcon.ORANGE, BgIcon.COOKIE)
            .shuffled()
            .subList(0, 2)
            .toImmutableList()
        Surface {
            MealsScreen(icons, {}, {})
        }
    }
}

