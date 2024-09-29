package com.am.recipe.presentation.model

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath

class ZigZagRec : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline =
        Outline.Generic(
            roundedZigZagAdageRec(size)
                .toPath()
                .asComposePath()
        )

    private fun roundedZigZagAdageRec(size: Size): RoundedPolygon {
        val u = size.height / 8f // unit
        val h = size.height
        val w = size.width
        return RoundedPolygon(
            vertices = arrayOf(
                // top left
                0f, u * 2,
                u, u * 2,
                u, u,
                u * 2, u,
                u * 2, 0f,
                // top right
                w, 0f,
                // bottom right
                w, h - u * 2,
                w - u, h - u * 2,
                w - u, h - u,
                w - u * 2, h - u,
                w - u * 2, h,
                // bottom left
                0f, h,
                // path closed be default
            ).toFloatArray(),
            centerX = 0f,
            centerY = 0f,
            rounding = CornerRounding(size.minDimension / 15F)
        )
    }

}