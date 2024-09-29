package com.am.recipe.presentation.ui.recipe

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.am.recipe.R
import com.am.recipe.domain.model.Recipe
import com.am.recipe.domain.model.RecipeState
import com.am.recipe.presentation.model.BgIcon
import com.am.recipe.presentation.ui.AppViewModelProvider
import com.am.recipe.presentation.ui.common.GlassyLayer
import com.am.recipe.presentation.ui.common.IconsBackGround
import com.am.recipe.presentation.ui.common.RecipeTopAppBar
import com.am.recipe.presentation.ui.home.ErrorMessCard
import com.am.recipe.presentation.ui.navigation.NavigationDestination
import com.am.recipe.presentation.ui.theme.RecipeTheme
import com.am.recipe.util.Util.getGoogleLink
import com.am.recipe.util.Util.getYoutubeLink
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList


object RecipeDestination: NavigationDestination {
    override val route = "recipe"
    override val titleRes = R.string.recipe_title
    const val RECIPE_ID = "recipeId"
    val routeWithArg = "$route/{$RECIPE_ID}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    viewModel: RecipeViewModel = viewModel(factory = AppViewModelProvider.viewModelFactory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = RecipeState.Loading
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RecipeTopAppBar(stringResource(RecipeDestination.titleRes), true, scrollBehavior)
        }
    ) {
        RecipeBody(
            uiState,
            viewModel::exportToLocalStorage,
            Modifier.padding(top = it.calculateTopPadding())
        )
    }
}

@Composable
fun RecipeBody(
    uiState: RecipeState,
    saveRecipe: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier){
        IconsBackGround(
            MaterialTheme.colorScheme.onBackground,
            MaterialTheme.colorScheme.tertiary,
            Modifier.fillMaxSize(),
            BgIcon.RECIPE,
            uiState is RecipeState.Loading,
            uiState is RecipeState.Error,
        )
        if (uiState is RecipeState.Error)
            ErrorMessCard(uiState.errorType, Modifier.fillMaxSize())
        else if (uiState is RecipeState.Success)
            RecipeContent(uiState.recipe, saveRecipe)
    }
}

@Composable
fun RecipeContent(
    recipe: Recipe,
    saveRecipe: () -> Unit,
    modifier: Modifier = Modifier
) {
    val smallPadding = dimensionResource(R.dimen.small_padding)
    Column(
        verticalArrangement = Arrangement.spacedBy(smallPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        RecipeHeader(
            recipe.title,
            recipe.thumb,
            recipe.youtubeLink,
            recipe.sourceLink,
            saveRecipe,
            Modifier.padding(smallPadding)
        )
        RecipeDescriptionCard(
            recipe.area,
            recipe.category,
            recipe.dateModified,
            recipe.description,
            Modifier.padding(smallPadding)
        )
        RecipeIngredientsCard(
            recipe.ingredientMeasureList.toImmutableList(),
            Modifier.padding(smallPadding)
        )
    }
}

@Composable
fun RecipeHeader(
    title: String,
    thumb: String,
    youtubeLink: String?,
    sourceLink: String?,
    saveRecipe: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        GlassyLayer(MaterialTheme.colorScheme.primary, Modifier.matchParentSize())
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Title(title, Modifier.padding(dimensionResource(R.dimen.tiny_padding)))
            RecipeThump(thumb)
            RecipeActions(
                title,
                youtubeLink,
                sourceLink,
                saveRecipe,
                Modifier.padding(dimensionResource(R.dimen.tiny_padding))
            )
        }
    }
}

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
fun RecipeThump(thump: String) {
    Card {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(thump)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.no_meals_24px),
            placeholder = painterResource(R.drawable.filled_skillet_24),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun RecipeActions(
    title: String,
    youtubeLink: String?,
    sourceLink: String?,
    saveRecipe: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
        modifier = modifier
    ) {
        val context = LocalContext.current
        val webIntent = remember { getIntent(getGoogleLink(sourceLink, title)) }
        val youtubeIntent = remember { getIntent(getYoutubeLink(youtubeLink, title)) }

        FilledTonalIconButton(
            onClick = {
                try { context.startActivity(webIntent) }
                catch (_: Exception){  }
            }
        ){
            Icon(
                painter = painterResource(R.drawable.link_24px),
                contentDescription = null
            )
        }

        FilledTonalIconButton(
            onClick = {
                try { context.startActivity(youtubeIntent) }
                catch (_: Exception){  }
            },
            shape = IconButtonDefaults.filledShape
        ) {
            Icon(
                painter = painterResource(R.drawable.play_circle_24px),
                contentDescription = null
            )
        }

        FilledTonalIconButton(onClick =  saveRecipe ) {
            Icon(
                painter = painterResource(R.drawable.download_24px),
                contentDescription = null
            )
        }
    }
}

private fun getIntent(link: String) =
    Intent(Intent.ACTION_VIEW, Uri.parse(link))

@Composable
fun RecipeDescriptionCard(
    area: String,
    category: String,
    lastModificationDate: String?,
    description: String,
    modifier: Modifier = Modifier
) {
    val tinyPadding = dimensionResource(R.dimen.tiny_padding)
    var expanded by remember { mutableStateOf(false) }
    val expandShrinkAction  = { expanded = !expanded }
    Box(modifier = modifier){
        GlassyLayer(
            color = MaterialTheme.colorScheme.primary,
            Modifier.matchParentSize()
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(tinyPadding)
        ) {
            Card(
                shape = RoundedCornerShape(topStart = tinyPadding, topEnd = tinyPadding),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(tinyPadding)
                ) {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = area,
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            }
            lastModificationDate?.let {
                Card(
                    shape = RoundedCornerShape(0.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = lastModificationDate,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(tinyPadding)
                    )
                }
            }
            Card(
                onClick = expandShrinkAction,
                shape = RoundedCornerShape(
                    bottomStart = tinyPadding,
                    bottomEnd = tinyPadding
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Text(
                    text = description,
                    maxLines = if (expanded) Int.MAX_VALUE else 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(tinyPadding)
                )
            }
        }
    }
}

@Composable
fun RecipeIngredientsCard(
    ingredientMeasureList: ImmutableList<String>,
    modifier: Modifier = Modifier
) {
    if (ingredientMeasureList.isEmpty()) return
    val tinyPadding = dimensionResource(R.dimen.tiny_padding)
    Box(modifier = modifier) {
        GlassyLayer(
            color = MaterialTheme.colorScheme.primary,
            Modifier.matchParentSize()
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(tinyPadding)
        ) {
            when (ingredientMeasureList.size) {
                1 ->
                    Card(
                        shape = RoundedCornerShape(topStart = tinyPadding, topEnd = tinyPadding),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IngredientText(ingredientMeasureList.first())
                    }

                2 ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Card(
                            shape = RoundedCornerShape(topStart = tinyPadding, topEnd = tinyPadding),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IngredientText(ingredientMeasureList.first())
                        }
                        Card(
                            shape = RoundedCornerShape(bottomStart = tinyPadding, bottomEnd = tinyPadding),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IngredientText(ingredientMeasureList.last())
                        }
                    }

                else -> Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        shape = RoundedCornerShape(topStart = tinyPadding, topEnd = tinyPadding),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IngredientText(ingredientMeasureList.first())
                    }
                    ingredientMeasureList.subList(1, ingredientMeasureList.size-1).forEach{ _ ->
                        Card(
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IngredientText(ingredientMeasureList.last())
                        }
                    }
                    Card(
                        shape = RoundedCornerShape(bottomStart = tinyPadding, bottomEnd = tinyPadding),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IngredientText(ingredientMeasureList.last())
                    }
                }
            }

        }
    }
}

@Composable
fun IngredientText(text: String) {
    Text(
        text.replace('#', ' '),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.tiny_padding))
    )
}

@Preview(showBackground = true)
@Composable
private fun RecipeContentPreview() {
    RecipeTheme(darkTheme = true) {
        Surface {
            val fakeRecipe = Recipe(
                "Title is title",
                "Area",
                "Category",
                "Thumb",
                "Description",
                listOf(
                    "aaaa#AAAAA",
                    "aaaa#AAAAA",
                    "aaaa#AAAAA",
                ),
                "15/65/2044",
                null,
                null
            )
            val uiState = RecipeState.Success(fakeRecipe)
//            val uiState = RecipeState.Error(ErrorType.IO_ERROR)
//            val uiState = RecipeState.Loading
            RecipeBody(uiState, {}, Modifier.fillMaxSize())
        }
    }
}

