package com.am.recipe.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.am.recipe.R
import com.am.recipe.presentation.ui.common.IconType
import com.am.recipe.presentation.ui.common.IconsBackGround
import com.am.recipe.presentation.ui.navigation.NavigationDestination
import com.am.recipe.presentation.ui.theme.RecipeTheme

object HomeDestination: NavigationDestination{
    override val route = "home"
    override val titleRes = R.string.home_title
}



@Composable
fun HomeScreen(
    itemsList: List<String>,
    onclick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        IconsBackGround(
            MaterialTheme.colorScheme.secondary,
            Modifier.fillMaxSize(),
            IconType.CATEGORY,
            false
        )
        ItemsList(
            itemsList,
            onclick,
            Modifier.padding(dimensionResource(R.dimen.small_padding))
        )
    }

}

@Composable
fun ItemsList(
    itemsList: List<String>,
    onclick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val itemModifier = Modifier
        .fillMaxWidth()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
        modifier = modifier
    ) {
        items(itemsList, key= { it } ){
            ItemCard(it, onclick, itemModifier)
        }
    }
}

@Composable
fun ItemCard(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.small_padding))
        )
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    RecipeTheme(darkTheme = true) {
        val area = listOf(
            "Egyptian",
            "American",
            "British",
            "Canadian",
            "Chinese",
            "Croatian",
            "Dutch",
            "Filipino",
            "French",
            "Greek"
        )
        HomeScreen(
            area,
            {},
        )
    }
}