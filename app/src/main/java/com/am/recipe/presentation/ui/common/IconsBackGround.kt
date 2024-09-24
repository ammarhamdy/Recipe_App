package com.am.recipe.presentation.ui.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.am.recipe.presentation.ui.theme.RecipeTheme
import kotlinx.coroutines.delay
import okhttp3.internal.toImmutableList

object IconPath {
    val category = PathParser().parsePathString(
        "M297,379L446,136Q452,126 461,121.5Q470,117 480,117Q490,117 499,121.5Q508,126 514,136L663,379Q669,389 669," +
                "400Q669,411 664,420Q659,429 650,434.5Q641,440 629,440L331,440Q319,440 310,434.5Q301,429 296,420Q291," +
                "411 291,400Q291,389 297,379ZM700,880Q625,880 572.5,827.5Q520,775 520,700Q520,625 572.5,572.5Q625,520 700," +
                "520Q775,520 827.5,572.5Q880,625 880,700Q880,775 827.5,827.5Q775,880 700,880ZM120,820L120,580Q120,563 131.5," +
                "551.5Q143,540 160,540L400,540Q417,540 428.5,551.5Q440,563 440,580L440,820Q440,837 428.5,848.5Q417,860 400," +
                "860L160,860Q143,860 131.5,848.5Q120,837 120,820Z"
    ).toNodes()
    val area = PathParser().parsePathString(
        "M480,880Q397,880 324,848.5Q251,817 197,763Q143,709 111.5,636Q80,563 80,480Q80,397 111.5," +
                "324Q143,251 197,197Q251,143 324,111.5Q397,80 480,80Q563,80 636,111.5Q709,143 763," +
                "197Q817,251 848.5,324Q880,397 880,480Q880,563 848.5,636Q817,709 763,763Q709,817 636," +
                "848.5Q563,880 480,880ZM440,798L440,720Q407,720 383.5,696.5Q360,673 360,640L360,600L168," +
                "408Q165,426 162.5,444Q160,462 160,480Q160,601 239.5,692Q319,783 440,798ZM716,696Q736,674 752," +
                "648.5Q768,623 778.5,595.5Q789,568 794.5,539Q800,510 800,480Q800,382 745.5,301Q691,220 600,184L600," +
                "200Q600,233 576.5,256.5Q553,280 520,280L440,280L440,360Q440,377 428.5,388.5Q417,400 400,400L320,400L320," +
                "480L560,480Q577,480 588.5,491.5Q600,503 600,520L600,640L640,640Q666,640 687,655.5Q708,671 716,696Z"
    ).toNodes()
    val ingredient = PathParser().parsePathString(
        "M640,880Q540,880 470,810Q400,740 400,640Q400,540 470,470Q540,400 640,400Q740,400 810,470Q880," +
                "540 880,640Q880,740 810,810Q740,880 640,880ZM640,800Q706,800 753,753Q800,706 800,640Q800," +
                "574 753,527Q706,480 640,480Q574,480 527,527Q480,574 480,640Q480,706 527,753Q574,800 640," +
                "800ZM160,800Q127,800 103.5,776.5Q80,753 80,720L80,416Q80,408 81.5,400Q83,392 86,384L166," +
                "200L160,200Q143,200 131.5,188.5Q120,177 120,160L120,120Q120,103 131.5,91.5Q143,80 160,80L440," +
                "80Q457,80 468.5,91.5Q480,103 480,120L480,160Q480,177 468.5,188.5Q457,200 440,200L434,200L500," +
                "352Q481,362 464,373Q447,384 432,398L348,200L252,200L160,416L160,720Q160,720 160,720Q160,720 160," +
                "720L330,720Q335,741 343.5,761.5Q352,782 364,800L160,800ZM640,360Q598,360 569,331Q540,302 540," +
                "260Q540,218 569,189Q598,160 640,160L640,360Q640,318 669,289Q698,260 740,260Q782,260 811,289Q840," +
                "318 840,360L640,360Z"
    ).toNodes()
}

object Rand{
    val rotations = listOf(-90f, -45f, 0f, 45f, 90f)
        .shuffled()
        .toImmutableList()
    val scales = listOf(.6f, .7f, .8f, .9f, 1f)
        .shuffled()
        .toImmutableList()
}


@Composable
fun IconsBackGround(
    color: Color,
    modifier: Modifier = Modifier,
    iconType: IconType = IconType.AREA,
    canAnimate: Boolean = true
) {
    val iScale = remember { Animatable(if (canAnimate) .1f else 1f) }
    val iScale2 = remember { Animatable(if (canAnimate) .1f else 1f) }
    val vectorPainter = rememberVectorPainter(
        defaultWidth = 32f.dp,
        defaultHeight = 32f.dp,
        viewportWidth = 960f,
        viewportHeight = 960f,
        autoMirror = true
    ) { _, _ ->
        Path(
            when(iconType) {
                IconType.AREA -> IconPath.area
                IconType.INGREDIENT -> IconPath.ingredient
                IconType.CATEGORY -> IconPath.category
            },
            fill= SolidColor(color)
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
                            rotate(Rand.rotations[(r+i)%5], vCenter)
                            scale(
                                Rand.scales[(r*i)%5] *
                                        if(r%2==0) iScale.value else iScale2.value,
                                vCenter
                            )
                            translate(left = x, top = y)
                        }
                    ){
                        draw(intrinsicSize, alpha = .5f)
                    }
                }
            }
        }
    }
    if (canAnimate)
        LaunchedEffect(Unit) {
            iScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            delay(700)
            iScale2.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        }
}

@Preview
@Composable
private fun IconsBackGroundPreview() {
    RecipeTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            IconsBackGround(
                MaterialTheme.colorScheme.secondary,
                Modifier.fillMaxSize(),
                IconType.INGREDIENT,
                true
            )
        }
    }
}