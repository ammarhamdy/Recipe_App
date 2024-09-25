package com.am.recipe.presentation.ui.common

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.am.recipe.R
import com.am.recipe.presentation.ui.theme.RecipeTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeBottomBar(
    onSelected: (IconType) -> Unit,
    currentPage: IconType
) {
    val iconColor = MaterialTheme.colorScheme.onSurface
    val vectorPainterList = listOf(
        rememberVectorPainter(
            defaultWidth = 24f.dp,
            defaultHeight = 24f.dp,
            viewportWidth = 960f,
            viewportHeight = 960f,
            autoMirror = true
        ){ _, _ ->
            Path(IconPath.area, fill=SolidColor(iconColor))
        },
        rememberVectorPainter(
            defaultWidth = 24f.dp,
            defaultHeight = 24f.dp,
            viewportWidth = 960f,
            viewportHeight = 960f,
            autoMirror = true
        ){ _, _ ->
            Path(IconPath.ingredient, fill=SolidColor(iconColor))
        },
        rememberVectorPainter(
            defaultWidth = 24f.dp,
            defaultHeight = 24f.dp,
            viewportWidth = 960f,
            viewportHeight = 960f,
            autoMirror = true
        ){ _, _ ->
            Path(IconPath.category, fill=SolidColor(iconColor))
        }
    )
    val keyVector = IconType.entries
        .zip(vectorPainterList)
        .toImmutableList()
    RoundedBottomBar(
        keyVector,
        currentPage,
        onSelected,
        iconColor,
        Modifier
            .height(barHeight)
            .fillMaxWidth()
    )
}

@Composable
fun RoundedBottomBar(
    keyVectors: ImmutableList<Pair<IconType, VectorPainter>>,
    currentPage: IconType,
    onSelected: (IconType) -> Unit,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .selectableGroup()
        ) {
            keyVectors.forEach { (page, vector) ->
                val action = { onSelected(page) }
                val isSelected = currentPage == page
                BottomBarTab(vector, isSelected, color, action)
            }
        }
    }
}

@Composable
fun BottomBarTab(
    vector: VectorPainter,
    isSelected: Boolean,
    color: Color,
    onSelected: () -> Unit
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val strokeScale by animateFloatAsState(
        targetValue = if (isSelected) 1f else .4f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "scale"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isSelected) 1.2f else 1f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "scale"
    )
    Box(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.small_padding))
            .height(barHeight)
            .selectable(
                selected = isSelected,
                onClick = onSelected,
                role = null,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(false, Dp.Unspecified, Color.Unspecified)
            )
    ){
        Image(
            painter = vector,
            contentDescription = null,
            modifier = Modifier
                .drawBehind {
                    drawCircle(
                        color = color,
                        radius = size.width * strokeScale,
                        style = Stroke(1.dp.toPx())
                    )
                    drawCircle(backgroundColor, size.width/2)
                }
                .graphicsLayer {
                    scaleX = iconScale
                    scaleY = iconScale
                }
        )
    }
}

@Preview
@Composable
private fun HomeBottomBarPreview() {
    RecipeTheme {
        HomeBottomBar({}, IconType.INGREDIENT)
    }
}

private val barHeight = 56.dp