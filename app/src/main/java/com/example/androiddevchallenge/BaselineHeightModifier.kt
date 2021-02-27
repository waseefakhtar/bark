package com.example.androiddevchallenge

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp

data class BaselineHeightModifier(
    val heightFromBaseline: Dp
) : LayoutModifier {

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {

        val textPlaceable = measurable.measure(constraints)
        val firstBaseline = textPlaceable[FirstBaseline]
        val lastBaseline = textPlaceable[LastBaseline]

        val height = heightFromBaseline.roundToPx() + lastBaseline - firstBaseline
        return layout(constraints.maxWidth, height) {
            val topY = heightFromBaseline.roundToPx() - firstBaseline
            textPlaceable.place(0, topY)
        }
    }
}

fun Modifier.baselineHeight(heightFromBaseline: Dp): Modifier =
    this.then(BaselineHeightModifier(heightFromBaseline))