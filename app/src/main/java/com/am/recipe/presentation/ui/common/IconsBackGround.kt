package com.am.recipe.presentation.ui.common

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.am.recipe.presentation.model.AniFactor
import com.am.recipe.presentation.model.BgIcon
import com.am.recipe.presentation.ui.theme.RecipeTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun IconsBackGround(
    color: Color,
    errorColor: Color,
    modifier: Modifier = Modifier,
    bgIcon: BgIcon = BgIcon.AREA,
    loadingAnimationIsActive: Boolean = false,
    errorAnimationIsActive: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label="infinite")
    val scale by if (loadingAnimationIsActive)
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    delayMillis = 0,
                    easing = AniFactor.BounceOutEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scale1"
        )
    else
        remember { mutableFloatStateOf(1f) }
    val isOnError by if(errorAnimationIsActive)
        infiniteTransition.animateValue(
            initialValue = true,
            targetValue = false,
            typeConverter = AniFactor.BooleanTwoWayConverter,
            animationSpec = AniFactor.BooleanAniSpec,
            label = "bool"
        )
    else
        remember { mutableStateOf(false) }

    val vectorPainter = rememberVectorPainter(
        defaultWidth = 24f.dp,
        defaultHeight = 24f.dp,
        viewportWidth = 960f,
        viewportHeight = 960f,
        autoMirror = true
    ) { _, _ ->
        Path(
            bgIcon.pathNodes,
            fill= SolidColor(if (isOnError) errorColor else color)
        )
    }

    Canvas(modifier){
        //.. vector ..\\
        val pad = vectorPainter.intrinsicSize.width / 3
        val vw = vectorPainter.intrinsicSize.width + pad
        val vh = vectorPainter.intrinsicSize.height + pad
        //.. canvas ..\\
        val width = size.width
        val height = size.height
        val edgeX = (width - ((width/vw).toInt() * vw)) / 2
        val rowHeight = vh * 2
        //.. draw vectors ..\\
        //.. number of rows ..\\
        repeat((height/(vh*2)).toInt()){ i ->
            val yi = i * rowHeight + pad
            //.. each row ..\\
            repeat((width/vw).toInt()){ r ->
                val x = edgeX + r.toFloat() * vw - (pad / 2)
                val y = if(r%2==0) vh + pad + yi else yi
                val vCenter = Offset(x+vw/2, y+vh/2)
                vectorPainter.run {
                    withTransform(
                        {
                            rotate(AniFactor.rotations[(r+i)%5], vCenter)
                            scale(AniFactor.scales[(r*i)%5] * scale, vCenter)
                            translate(left = x, top = y)
                        }
                    ){
                        draw(intrinsicSize, alpha = .5f)
                    }
                }
            }
        }
    }
}

@Composable
fun MultiIconsBackGround(
    color: Color,
    errorColor: Color,
    bgIcons: ImmutableList<BgIcon>,
    modifier: Modifier = Modifier,
    loadingAnimationIsActive: Boolean = false,
    errorAnimationIsActive: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label="infinite")
    val scale by if (loadingAnimationIsActive)
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    delayMillis = 0,
                    easing = AniFactor.BounceOutEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scale1"
        )
    else
        remember { mutableFloatStateOf(1f) }
    val isOnError by if(errorAnimationIsActive)
        infiniteTransition.animateValue(
            initialValue = true,
            targetValue = false,
            typeConverter = AniFactor.BooleanTwoWayConverter,
            animationSpec = AniFactor.BooleanAniSpec,
            label = "bool"
        )
    else
        remember { mutableStateOf(false) }

    val vectorPainters =  bgIcons.map {
        rememberVectorPainter(
            defaultWidth = 32f.dp,
            defaultHeight = 32f.dp,
            viewportWidth = 960f,
            viewportHeight = 960f,
            autoMirror = true
        ) { _, _ ->
            Path(
                it.pathNodes,
                fill= SolidColor(if (isOnError) errorColor else color)
            )
        }
    }.toImmutableList()


    Canvas(modifier){
        val vectorPainter = vectorPainters.first()
        //.. vector ..\\
        val pad = vectorPainter.intrinsicSize.width / 3
        val vw = vectorPainter.intrinsicSize.width + pad
        val vh = vectorPainter.intrinsicSize.height + pad
        //.. canvas ..\\
        val width = size.width
        val height = size.height
        val edgeX = (width - ((width/vw).toInt() * vw)) / 2
        val rowHeight = vh * 2
        //.. draw vectors ..\\
        //.. number of rows ..\\
        repeat((height/(vh*2)).toInt()){ i ->
            val yi = i * rowHeight + pad
            val vector = vectorPainters[i%vectorPainters.size]
            //.. each row ..\\
            repeat((width/vw).toInt()){ r ->
                val x = edgeX + r.toFloat() * vw - (pad / 2)
                val y = if(r%2==0) vh + pad + yi else yi
                val vCenter = Offset(x+vw/2, y+vh/2)
                vector.run {
                    withTransform(
                        {
                            rotate(AniFactor.rotations[(r+i)%5], vCenter)
                            scale(AniFactor.scales[(r*i)%5] * scale, vCenter)
                            translate(left = x, top = y)
                        }
                    ){
                        draw(intrinsicSize, alpha = .5f)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun IconsBackGroundPreview() {
    RecipeTheme(darkTheme = true) {
        Surface {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                IconsBackGround(
                    color = MaterialTheme.colorScheme.onSurface,
                    errorColor = MaterialTheme.colorScheme.tertiary,
                    Modifier.fillMaxSize(),
                    bgIcon = BgIcon.RECIPE,
                    true
                )
//                MultiIconsBackGround(
//                    MaterialTheme.colorScheme.onSurface,
//                    MaterialTheme.colorScheme.tertiary,
//                    listOf(BgIcon.EGG, BgIcon.PIZZA, BgIcon.ORANGE)
//                        .shuffled()
//                        .toImmutableList(),
//                    Modifier.fillMaxSize(),
//                    true,
//                    errorAnimationIsActive = false
//                )
            }
        }
    }
}
