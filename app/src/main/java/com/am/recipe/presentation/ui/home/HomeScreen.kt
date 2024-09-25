package com.am.recipe.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.am.recipe.R
import com.am.recipe.presentation.ui.common.ErrorPage
import com.am.recipe.presentation.ui.common.ErrorType
import com.am.recipe.presentation.ui.common.HomeBottomBar
import com.am.recipe.presentation.ui.common.IconType
import com.am.recipe.presentation.ui.common.IconsBackGround
import com.am.recipe.presentation.ui.common.MealResponseState
import com.am.recipe.presentation.ui.common.RecipeTopAppBar
import com.am.recipe.presentation.ui.navigation.NavigationDestination
import com.am.recipe.presentation.ui.theme.RecipeTheme
import kotlinx.collections.immutable.toImmutableList

object HomeDestination: NavigationDestination{
    override val route = "home"
    override val titleRes = R.string.home_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToMealList: () -> Unit,
//    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.viewModelFactory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var page by remember { mutableStateOf(IconType.INGREDIENT) }
    val onSelectPage = { newPage: IconType -> page = newPage  }
    val uiState = when(page){
        IconType.AREA -> MealResponseState.Loading
        IconType.INGREDIENT -> MealResponseState.Error(ErrorType.IO_ERROR)
        IconType.CATEGORY -> MealResponseState.Success(fakeArea)
    }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RecipeTopAppBar(
                when(page){
                    IconType.AREA -> stringResource(R.string.area)
                    IconType.INGREDIENT -> stringResource(R.string.ingredient)
                    IconType.CATEGORY -> stringResource(R.string.category)
                },
                scrollBehavior
            )
        },
        bottomBar = {
            HomeBottomBar(onSelected = onSelectPage, currentPage = page)
        }
    ) {
        HomeBody(
            page,
            uiState,
            navigateToMealList,
            Modifier
                .fillMaxSize()
                .padding(it))
    }
}

@Composable
fun HomeBody(
    page: IconType,
    uiState: MealResponseState,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        IconsBackGround(
            MaterialTheme.colorScheme.onSurface,
            MaterialTheme.colorScheme.tertiary,
            Modifier.fillMaxSize(),
            page,
            uiState is MealResponseState.Loading,
            uiState is MealResponseState.Error,
        )
        if (uiState is MealResponseState.Error)
            ErrorMessCard(uiState.errorType, Modifier.fillMaxSize())
        else if (uiState is MealResponseState.Success)
            ItemsList(
                uiState.items,
                onItemClicked,
                Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.small_padding))
            )
    }
}

@Composable
fun ErrorMessCard(
    errorType: ErrorType,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ){
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.large_padding))
                .background(MaterialTheme.colorScheme.onBackground)
        )
        ErrorPage(errorType)
    }
}

@Composable
fun ItemsList(
    itemsList: List<String>,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val itemModifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy((-32).dp),
        modifier = modifier
    ) {
        itemsIndexed(itemsList, key= { index, _ -> index } ){ index, item ->
            ItemCard(item, onItemClicked, itemModifier, index%2 == 0)
        }
    }
}

@Composable
fun ItemCard(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    left: Boolean
) {
    val color = MaterialTheme.colorScheme.primary
    Box(
        modifier = modifier,
        contentAlignment = if (left) Alignment.CenterStart else Alignment.CenterEnd
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.47f)
                .fillMaxHeight()
                .graphicsLayer {
                    shape = RoundedCornerShape(8.dp)
                    clip = true
                    alpha = 0.25f
                }
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            color.copy(alpha = 0.4f),
                            color.copy(alpha = 0.15f)
                        )
                    )
                )
        )
        Card(
            onClick = onClick,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth(0.47f)
                .fillMaxHeight()
                .graphicsLayer {
                    scaleX = .9f
                    scaleY = .9f
                }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.small_padding))
                )
            }
        }
    }
}
/*

* */
@Preview
@Composable
private fun HomeScreenPreview() {
    RecipeTheme(darkTheme = true) {
        HomeScreen {}
    }
}

@Preview
@Composable
private fun ItemsListScreenPreview() {
    RecipeTheme(darkTheme = true) {
        val uiState = MealResponseState.Success(fakeArea)
        HomeBody(
            IconType.AREA,
            uiState,
            {},
        )
    }
}

val fakeArea = listOf(
    "Egyptian",
    "American",
    "British",
    "Canadian",
    "Chinese",
    "Croatian",
    "Dutch",
    "Filipino",
    "French",
    "Greek",
    "Egyptian",
    "American",
    "British",
    "Canadian",
    "Chinese",
    "Croatian",
    "Dutch",
    "Filipino",
    "French",
    "Greek",
).toImmutableList()