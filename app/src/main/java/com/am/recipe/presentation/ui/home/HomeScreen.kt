package com.am.recipe.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.am.recipe.R
import com.am.recipe.domain.model.SearchKeyState
import com.am.recipe.domain.model.SearchType
import com.am.recipe.domain.model.anType
import com.am.recipe.presentation.model.HomePage
import com.am.recipe.presentation.ui.AppViewModelProvider
import com.am.recipe.presentation.ui.common.ErrorMessCard
import com.am.recipe.presentation.ui.common.GlassyLayer
import com.am.recipe.presentation.ui.common.HomeBottomBar
import com.am.recipe.presentation.ui.common.RecipeTopAppBar
import com.am.recipe.presentation.ui.common.WaveIconsBackGround
import com.am.recipe.presentation.ui.navigation.NavigationDestination
import com.am.recipe.presentation.ui.theme.RecipeTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.delay

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
    val pullToRefresh = rememberPullToRefreshState()
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
            Box(modifier = Modifier.padding(top = dimensionResource(R.dimen.tiny_padding), bottom = 30.dp)){
                HomeBottomBar(onSelected = onSelectPage, currentPage = page)
            }
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .nestedScroll(pullToRefresh.nestedScrollConnection)
        ) {
            HomeBody(
                page,
                uiState.value,
                navigateToMealList,
                Modifier
                    .fillMaxSize()
                    .padding(it)
            )
            PullToRefreshContainer(
                state = pullToRefresh,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(it)
            )
        }
        if(pullToRefresh.isRefreshing){
            LaunchedEffect(true) {
                when(page){
                    HomePage.AREA ->  viewModel.reloadAreas()
                    HomePage.INGREDIENT -> viewModel.reloadIngredients()
                    HomePage.CATEGORY -> viewModel.reloadCategories()
                }
            }
        }
        when(uiState.value){
            is SearchKeyState.Error -> LaunchedEffect(true) {
                delay(500L)
                pullToRefresh.endRefresh()
            }
            SearchKeyState.Loading -> Unit
            is SearchKeyState.Success -> LaunchedEffect(true) {
                delay(300L)
                pullToRefresh.endRefresh()
            }
            is SearchKeyState.SuccessWithGroups -> LaunchedEffect(true) {
                delay(300L)
                pullToRefresh.endRefresh()
            }
        }
    }
}

@Composable
fun HomeBody(
    page: HomePage,
    uiState: SearchKeyState,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val parentSize = remember { mutableIntStateOf(0) }
    Box(
        modifier.onGloballyPositioned { parentSize.intValue = it.size.height }
    ) {
        WaveIconsBackGround(
            MaterialTheme.colorScheme.onSurface,
            MaterialTheme.colorScheme.tertiary,
            parentSize.intValue,
            page.bgIcon,
            uiState.anType(),
        )
        when (uiState) {
            is SearchKeyState.Error -> ErrorMessCard(
                uiState.errorType,
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
            is SearchKeyState.Success -> ItemsList(
                uiState.keys.toImmutableList(),
                page.toSearchType(),
                onItemClicked,
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.small_padding))
            )
            is SearchKeyState.SuccessWithGroups -> GroupedItemsList(
                uiState.keys.toImmutableMap(),
                onItemClicked,
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.small_padding))
            )
            SearchKeyState.Loading -> Unit
        }
    }
}

@Composable
fun ItemsList(
    itemsList: ImmutableList<String>,
    searchType: SearchType,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val itemModifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy((-32).dp),
        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.tiny_padding)),
        modifier = modifier
    ) {
        itemsIndexed(itemsList, key= { index, _ -> index } ){ index, item ->
            ItemCard(item, { onItemClicked(searchType.value+item) }, itemModifier, index%2 == 0)
        }
    }
}

@Composable
fun GroupedItemsList(
    itemsMap: ImmutableMap<Char, List<String>>,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val itemModifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy((-32).dp),
        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.tiny_padding)),
        modifier = modifier
    ) {
        itemsMap.forEach { (k, v) ->
            item {ListDivider(k)  }
            itemsIndexed(v) { index, item ->
                ItemCard(
                    item,
                    { onItemClicked(SearchType.BY_INGREDIENT.value+item) },
                    itemModifier,
                    index%2 == 0
                )
            }
        }
    }
}

@Composable
fun ListDivider(letter: Char) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ){
        HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
        Text(
            text = letter.uppercase(),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(4.dp)
        )
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
            elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation)),
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

//@Preview
//@Composable
//private fun HomeScreenPreview() {
//    RecipeTheme(darkTheme = true) {
//        HomeScreen({})
//    }
//}

@Preview
@Composable
private fun ItemsIngredientListScreenPreview() {
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
        )
            .groupBy { it.lowercase().first() }
            .toSortedMap()
        val uiState = SearchKeyState.SuccessWithGroups(fakeArea)
        HomeBody(
            HomePage.INGREDIENT,
            uiState,
            {},
        )
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
