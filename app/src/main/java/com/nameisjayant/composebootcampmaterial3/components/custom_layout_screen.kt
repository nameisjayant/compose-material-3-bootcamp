package com.nameisjayant.composebootcampmaterial3.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CustomLayoutScreen() {

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Hello world", modifier = Modifier.paddingFromBaseline(top = 20.dp))
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "Hello world", modifier = Modifier.padding(top = 20.dp))
    }

    /*BasicColumn(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Text("MyBasicColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }*/
}

fun Modifier.firstBaseLineToTop(
    firstBaseLineToTop: Dp
) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseLine = placeable[FirstBaseline]
    val placeableY = firstBaseLineToTop.roundToPx() - firstBaseLine
    val height = placeable.height - placeableY
    layout(placeable.width, height) {
        placeable.placeRelative(0, placeableY)
    }
}


@Composable
fun BasicColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = { measurables, constraints ->
            val placeable = measurables.map { measurable ->
                measurable.measure(constraints)
            }
            layout(constraints.maxWidth, constraints.maxHeight) {
                var yPosition = 0

                placeable.forEach { placeable ->
                    placeable.placeRelative(x = 0, y = yPosition)
                    yPosition += placeable.height
                }
            }
        }
    )
}










































