package com.am.recipe.presentation.ui.common

import androidx.annotation.StringRes
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.am.recipe.R
import com.am.recipe.presentation.model.AniFactor
import com.am.recipe.presentation.model.ErrorType
import com.am.recipe.presentation.ui.theme.RecipeTheme

@Composable
fun AlertMessCard(
    @StringRes textId: Int,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ){
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.large_padding))
                .background(MaterialTheme.colorScheme.onBackground)
        )
        AlertPage(
            textId,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.background,
        )
    }
}

@Composable
fun ErrorMessCard(
    errorType: ErrorType,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ){
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.large_padding))
                .background(MaterialTheme.colorScheme.onBackground)
        )
        AlertPage(
            when(errorType){
                ErrorType.UNEXPECTED_ERROR -> R.string.unexpected_error
                ErrorType.IO_ERROR -> R.string.connection_error
                ErrorType.HTTP_ERROR -> R.string.http_error
            },
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.background,
        )
    }
}

@Composable
private fun AlertPage(
    @StringRes textId: Int,
    color1: Color,
    color2: Color,
    modifier: Modifier = Modifier,
    reload: (() -> Unit)? = null
) {
    val message = stringResource(textId)
    reload?.let {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            TogglingText(message, color1, color2)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.small_padding)))
            TextButton(onClick = reload) {
                Text(text = stringResource(R.string.reload))
            }
        }
    } ?:
    TogglingText(message, color1, color2)
}

@Composable
private fun TogglingText(
    text: String,
    color1: Color,
    color2: Color,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite_transition")
    val display by infiniteTransition.animateValue(
        initialValue = true,
        targetValue = false,
        typeConverter = AniFactor.BooleanTwoWayConverter,
        animationSpec = AniFactor.BooleanAniSpec,
        label = "display_animation"
    )
    Box(modifier = Modifier.wrapContentSize()){
        if (display)
            Text(
                coloredTextBaseCondition(
                    text,
                    color1,
                    color2
                ) { n -> n % 2 == 0 },
                fontSize = 30.sp
            )
        else
            Text(
                coloredTextBaseCondition(
                    text,
                    color1,
                    color2
                ) { it % 2 != 0 },
                fontSize = 30.sp
            )
    }
}

fun coloredTextBaseCondition(
    text: String,
    color: Color,
    defaultColor: Color,
    condition: (Int)-> Boolean
) = buildAnnotatedString {
    val length = text.length
    var index = 0
    text.forEachIndexed{ i, c ->
        index += (i + (if (c==' ') 1 else 0)) % length // skip space character.
        if (condition(index))
            withStyle(style = SpanStyle(color, fontWeight = FontWeight.Bold)) { append(c) }
        else
            withStyle(style = SpanStyle(defaultColor)){ append(c) }
        index -= i // get back normal index sequence.
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorPagePreview() {
    RecipeTheme {
        AlertPage(
            R.string.unexpected_error,
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.onBackground
        )
    }
}