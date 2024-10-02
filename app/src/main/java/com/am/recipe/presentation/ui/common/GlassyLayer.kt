package com.am.recipe.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun GlassyLayer(
    color: Color,
    modifier:Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .graphicsLayer {
                shape = RoundedCornerShape(8.dp)
                clip = true
                alpha = 0.25f
            }
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        color.copy(alpha = 0.5f),
                        color.copy(alpha = 0.25f)
                    )
                )
            )
    )
}