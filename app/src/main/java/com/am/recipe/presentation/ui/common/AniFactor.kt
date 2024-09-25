package com.am.recipe.presentation.ui.common

import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import okhttp3.internal.toImmutableList

object AniFactor{

    val rotations = listOf(-90f, -45f, 0f, 45f, 90f)
        .shuffled()
        .toImmutableList()

    val scales = listOf(.6f, .7f, .8f, .9f, 1f)
        .shuffled()
        .toImmutableList()

    val BounceOutEasing = Easing { fraction ->
        when {
            fraction < 1 / 2.75f -> 7.5625f * fraction * fraction
            fraction < 2 / 2.75f -> {
                val t = fraction - 1.5f / 2.75f
                7.5625f * t * t + 0.75f
            }
            fraction < 2.5 / 2.75 -> {
                val t = fraction - 2.25f / 2.75f
                7.5625f * t * t + 0.9375f
            }
            else -> {
                val t = fraction - 2.625f / 2.75f
                7.5625f * t * t + 0.984375f
            }
        }
    }

    val BooleanTwoWayConverter = TwoWayConverter<Boolean, AnimationVector1D>(
        convertToVector = { bool -> AnimationVector1D(if (bool) 1f else 0f) },
        convertFromVector = { vector -> vector.value == 1f }
    )

    val BooleanAniSpec = infiniteRepeatable<Boolean>(
        tween(2000, delayMillis = 2000, easing = LinearEasing),
        repeatMode = RepeatMode.Restart
    )
}