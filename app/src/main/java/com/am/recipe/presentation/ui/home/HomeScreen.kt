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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.am.recipe.R
import com.am.recipe.presentation.ui.common.ErrorPage
import com.am.recipe.presentation.model.ErrorType
import com.am.recipe.presentation.ui.common.HomeBottomBar
import com.am.recipe.presentation.ui.common.IconsBackGround
import com.am.recipe.domain.model.SearchKeyState
import com.am.recipe.presentation.model.HomePage
import com.am.recipe.presentation.ui.AppViewModelProvider
import com.am.recipe.presentation.ui.common.GlassyLayer
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
    navigateToMealList: (String) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.viewModelFactory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var page by remember { mutableStateOf(HomePage.INGREDIENT) }
    val onSelectPage = { newPage: HomePage -> page = newPage  }
    val uiState = when(page){
        HomePage.AREA -> viewModel.areaUiState.collectAsState()
        HomePage.INGREDIENT -> viewModel.ingredientsUiState.collectAsState()
        HomePage.CATEGORY -> viewModel.categoryUiState.collectAsState()
    }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RecipeTopAppBar(
                when(page){
                    HomePage.AREA -> stringResource(R.string.area)
                    HomePage.INGREDIENT -> stringResource(R.string.ingredient)
                    HomePage.CATEGORY -> stringResource(R.string.category)
                },
                false,
                scrollBehavior
            )
        },
        bottomBar = {
            HomeBottomBar(onSelected = onSelectPage, currentPage = page)
        }
    ) {
        HomeBody(
            page,
            uiState.value,
            navigateToMealList,
            Modifier
                .fillMaxSize()
                .padding(it))
    }
}

@Composable
fun HomeBody(
    page: HomePage,
    uiState: SearchKeyState,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        IconsBackGround(
            MaterialTheme.colorScheme.onSurface,
            MaterialTheme.colorScheme.tertiary,
            Modifier.fillMaxSize(),
            page.bgIcon,
            uiState is SearchKeyState.Loading,
            uiState is SearchKeyState.Error,
        )
        if (uiState is SearchKeyState.Error)
            ErrorMessCard(uiState.errorType, Modifier.fillMaxSize())
        else if (uiState is SearchKeyState.Success)
            ItemsList(
                uiState.keys,
                onItemClicked,
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.small_padding))
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
    onItemClicked: (String) -> Unit,
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
            ItemCard(item, { onItemClicked(item) }, itemModifier, index%2 == 0)
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
        GlassyLayer(color,
            Modifier
                .fillMaxWidth(.47f)
                .fillMaxHeight())
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
                modifier = Modifier.fillMaxSize()
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

@Preview
@Composable
private fun HomeScreenPreview() {
    RecipeTheme(darkTheme = true) {
        HomeScreen({})
    }
}

@Preview
@Composable
private fun ItemsListScreenPreview() {
    RecipeTheme(darkTheme = true) {
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
        val uiState = SearchKeyState.Success(fakeArea)
        HomeBody(
            HomePage.AREA,
            uiState,
            {},
        )
    }
}
