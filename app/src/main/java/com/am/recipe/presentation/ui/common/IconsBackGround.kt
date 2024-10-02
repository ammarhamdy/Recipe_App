package com.am.recipe.presentation.ui.common

import androidx.compose.animation.core.Animatable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.am.recipe.domain.model.AnimationType
import com.am.recipe.presentation.model.AniFactor
import com.am.recipe.presentation.model.BgIcon
import com.am.recipe.presentation.ui.theme.RecipeTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@Composable
fun WaveMultiIconsBackGround(
    color: Color,
    errorColor: Color,
    screenHeight: Int,
    bgIcons: ImmutableList<BgIcon>,
    type: AnimationType
) {
    val infiniteTransition = rememberInfiniteTransition(label="infinite")

    ///.. vector ..\\\
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
                fill= SolidColor(color.copy(alpha = .4f)
                )
            )
        }
    }.toImmutableList()

    ///.. drawing properties ..\\\
    val vectorPainter = vectorPainters.first()
    val pad = vectorPainter.intrinsicSize.width / 3
    val vw = vectorPainter.intrinsicSize.width + pad
    val vh = vectorPainter.intrinsicSize.height + pad
    val nRows = (screenHeight/(vh*2)).toInt()

    ///.. alert animation ..\\
    when (type) {
        AnimationType.ALERT -> {
            val isOnError by infiniteTransition.animateValue(
                    initialValue = true,
                    targetValue = false,
                    typeConverter = AniFactor.BooleanTwoWayConverter,
                    animationSpec = AniFactor.BooleanAniSpec,
                    label = "bool"
                )
            Canvas(Modifier.fillMaxSize()){
                //.. canvas ..\\
                val width = size.width
                val height = size.height
                val edgeX = (width - ((width/vw).toInt() * vw)) / 2
                val edgeY = (height - ((height/vh).toInt() * vh)) / 2
                val rowHeight = vh * 2
                //.. draw vectors ..\\
                //.. number of rows ..\\
                repeat(nRows){ i ->
                    val yi = i * rowHeight + pad
                    val vector = vectorPainters[i%vectorPainters.size]
                    //.. each row ..\\
                    repeat((width/vw).toInt()){ r ->
                        val x = (r.toFloat() * vw - (pad / 2)) + edgeX
                        val y = (if(r%2==0) vh + pad + yi else yi) + edgeY
                        val vCenter = Offset(x+vw/2, y+vh/2)
                        vector.run {
                            withTransform(
                                {
                                    rotate(AniFactor.rotations[(r+i)%5], vCenter)
                                    scale(AniFactor.scales[(r*i)%5], vCenter)
                                    translate(left = x, top = y)
                                }
                            ){
                                draw(
                                    intrinsicSize,
                                    colorFilter =
                                    if (isOnError) ColorFilter.tint(color)
                                    else ColorFilter.tint(errorColor)
                                )
                            }
                        }
                    }
                }
            }
        }

        ///.. loading animation ..\\\
        AnimationType.LOADING -> {
            val scale = infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, 0, AniFactor.BounceOutEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "scale1"
            )
            Canvas(Modifier.fillMaxSize()){
                //.. canvas ..\\
                val width = size.width
                val height = size.height
                val edgeX = (width - ((width/vw).toInt() * vw)) / 2
                val edgeY = (height - ((height/vh).toInt() * vh)) / 2
                val rowHeight = vh * 2
                //.. draw vectors ..\\
                //.. number of rows ..\\
                repeat(nRows){ i ->
                    val yi = i * rowHeight + pad
                    val vector = vectorPainters[i%vectorPainters.size]
                    //.. each row ..\\
                    repeat((width/vw).toInt()){ r ->
                        val x = (r.toFloat() * vw - (pad / 2)) + edgeX
                        val y = (if(r%2==0) vh + pad + yi else yi) + edgeY
                        val vCenter = Offset(x+vw/2, y+vh/2)
                        vector.run {
                            withTransform(
                                {
                                    rotate(AniFactor.rotations[(r+i)%5], vCenter)
                                    scale(AniFactor.scales[(r*i)%5] * scale.value, vCenter)
                                    translate(left = x, top = y)
                                }
                            ){
                                draw(intrinsicSize)
                            }
                        }
                    }
                }
            }
        }
        ///.. success animation ..\\\
        AnimationType.SUCCESS -> {
            val scales = List(nRows){ remember { Animatable(0f) } }
            Canvas(Modifier.fillMaxSize()){
                //.. canvas ..\\
                val width = size.width
                val height = size.height
                val edgeX = (width - ((width/vw).toInt() * vw)) / 2
                val edgeY = (height - ((height/vh).toInt() * vh)) / 2
                val rowHeight = vh * 2
                //.. draw vectors ..\\
                //.. number of rows ..\\
                repeat(nRows){ i ->
                    val yi = i * rowHeight + pad
                    val vector = vectorPainters[i%vectorPainters.size]
                    //.. each row ..\\
                    repeat((width/vw).toInt()){ r ->
                        val x = (r.toFloat() * vw - (pad / 2)) + edgeX
                        val y = (if(r%2==0) vh + pad + yi else yi) + edgeY
                        val vCenter = Offset(x+vw/2, y+vh/2)
                        vector.run {
                            withTransform(
                                {
                                    rotate(AniFactor.rotations[(r+i)%5], vCenter)
                                    scale(AniFactor.scales[(r*i)%5] * scales[i].value, vCenter)
                                    translate(left = x, top = y)
                                }
                            ){
                                draw(intrinsicSize)
                            }
                        }
                    }
                }
            }
            LaunchedEffect(Unit) {
                scales.forEachIndexed { i, it ->
                    launch { it.animateTo(1f, tween(200, 150*i, AniFactor.BounceOutEasing)) }
                }
            }
        }
    }
}

@Composable
fun WaveIconsBackGround(
    color: Color,
    errorColor: Color,
    screenHeight: Int,
    bgIcon: BgIcon = BgIcon.AREA,
    type: AnimationType
) {
    val infiniteTransition = rememberInfiniteTransition(label="infinite")

    ///.. vector ..\\\
    val vectorPainter = rememberVectorPainter(
        defaultWidth = 32f.dp,
        defaultHeight = 32f.dp,
        viewportWidth = 960f,
        viewportHeight = 960f,
        autoMirror = true
    ) { _, _ ->
        Path(bgIcon.pathNodes, fill = SolidColor(color.copy(alpha = .4f)))
    }

    ///.. drawing properties ..\\\
    val pad = vectorPainter.intrinsicSize.width / 3
    val vw = vectorPainter.intrinsicSize.width + pad
    val vh = vectorPainter.intrinsicSize.height + pad
    val nRows = (screenHeight/(vh*2)).toInt()

    ///.. alert animation ..\\
    when (type) {
        AnimationType.ALERT -> {
            val isOnError by infiniteTransition.animateValue(
                    initialValue = true,
                    targetValue = false,
                    typeConverter = AniFactor.BooleanTwoWayConverter,
                    animationSpec = AniFactor.BooleanAniSpec,
                    label = "bool"
                )
            Canvas(Modifier.fillMaxSize()){
                //.. canvas ..\\
                val width = size.width
                val height = size.height
                val edgeX = (width - ((width/vw).toInt() * vw)) / 2
                val edgeY = (height - ((height/vh).toInt() * vh)) / 2
                val rowHeight = vh * 2
                //.. draw vectors ..\\
                //.. number of rows ..\\
                repeat(nRows){ i ->
                    val yi = i * rowHeight + pad
                    //.. each row ..\\
                    repeat((width/vw).toInt()){ r ->
                        val x = (r.toFloat() * vw - (pad / 2)) + edgeX
                        val y = (if(r%2==0) vh + pad + yi else yi) + edgeY
                        val vCenter = Offset(x+vw/2, y+vh/2)
                        vectorPainter.run {
                            withTransform(
                                {
                                    rotate(AniFactor.rotations[(r+i)%5], vCenter)
                                    scale(AniFactor.scales[(r*i)%5], vCenter)
                                    translate(left = x, top = y)
                                }
                            ){
                                draw(
                                    intrinsicSize,
                                    colorFilter =
                                    if (isOnError) ColorFilter.tint(color)
                                    else ColorFilter.tint(errorColor)
                                )
                            }
                        }
                    }
                }
            }
        }

        ///.. loading animation ..\\\
        AnimationType.LOADING -> {
            val scale = infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, 0, AniFactor.BounceOutEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "scale1"
            )
            Canvas(Modifier.fillMaxSize()){
                //.. canvas ..\\
                val width = size.width
                val height = size.height
                val edgeX = (width - ((width/vw).toInt() * vw)) / 2
                val edgeY = (height - ((height/vh).toInt() * vh)) / 2
                val rowHeight = vh * 2
                //.. draw vectors ..\\
                //.. number of rows ..\\
                repeat(nRows){ i ->
                    val yi = i * rowHeight + pad
                    //.. each row ..\\
                    repeat((width/vw).toInt()){ r ->
                        val x = (r.toFloat() * vw - (pad / 2)) + edgeX
                        val y = (if(r%2==0) vh + pad + yi else yi) + edgeY
                        val vCenter = Offset(x+vw/2, y+vh/2)
                        vectorPainter.run {
                            withTransform(
                                {
                                    rotate(AniFactor.rotations[(r+i)%5], vCenter)
                                    scale(AniFactor.scales[(r*i)%5] * scale.value, vCenter)
                                    translate(left = x, top = y)
                                }
                            ){
                                draw(intrinsicSize)
                            }
                        }
                    }
                }
            }
        }
        ///.. success animation ..\\\
        AnimationType.SUCCESS -> {
            val scales = List(nRows){ remember { Animatable(0f) } }
            Canvas(Modifier.fillMaxSize()){
                //.. canvas ..\\
                val width = size.width
                val height = size.height
                val edgeX = (width - ((width/vw).toInt() * vw)) / 2
                val edgeY = (height - ((height/vh).toInt() * vh)) / 2
                val rowHeight = vh * 2
                //.. draw vectors ..\\
                //.. number of rows ..\\
                repeat(nRows){ i ->
                    val yi = i * rowHeight + pad
                    //.. each row ..\\
                    repeat((width/vw).toInt()){ r ->
                        val x = (r.toFloat() * vw - (pad / 2)) + edgeX
                        val y = (if(r%2==0) vh + pad + yi else yi) + edgeY
                        val vCenter = Offset(x+vw/2, y+vh/2)
                        vectorPainter.run {
                            withTransform(
                                {
                                    rotate(AniFactor.rotations[(r+i)%5], vCenter)
                                    scale(AniFactor.scales[(r*i)%5] * scales[i].value, vCenter)
                                    translate(left = x, top = y)
                                }
                            ){
                                draw(intrinsicSize)
                            }
                        }
                    }
                }
            }
            LaunchedEffect(Unit) {
                scales.forEachIndexed { i, it ->
                    launch { it.animateTo(1f, tween(200, 150*i, AniFactor.BounceOutEasing)) }
                }
            }
        }
    }
}


@Preview
@Composable
private fun WaveIconsBackGroundPreview() {
    RecipeTheme(darkTheme = true) {
        Surface {
            var parentSize by remember { mutableStateOf(IntSize.Zero) }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .onGloballyPositioned { parentSize = it.size }
            ) {
                WaveIconsBackGround(
                    color = MaterialTheme.colorScheme.onSurface,
                    errorColor = MaterialTheme.colorScheme.tertiary,
                    parentSize.height,
                    bgIcon = BgIcon.CATEGORY,
                    AnimationType.ALERT
                )
            }
        }
    }
}

@Preview
@Composable
private fun WaveMultiIconsBackGroundPreview() {
    RecipeTheme(darkTheme = true) {
        Surface {
            var parentSize by remember { mutableStateOf(IntSize.Zero) }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .onGloballyPositioned { parentSize = it.size }
            ) {
                WaveMultiIconsBackGround(
                    color = MaterialTheme.colorScheme.onSurface,
                    errorColor = MaterialTheme.colorScheme.tertiary,
                    parentSize.height,
                    bgIcons = listOf(BgIcon.PIZZA, BgIcon.EGG).toImmutableList(),
                    AnimationType.ALERT
                )
            }
        }
    }
}