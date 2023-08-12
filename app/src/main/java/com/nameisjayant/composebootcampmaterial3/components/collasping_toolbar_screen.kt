package com.nameisjayant.composebootcampmaterial3.components

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.nameisjayant.composebootcampmaterial3.R
import kotlin.math.max
import kotlin.math.min

private val BottomBarHeight = 56.dp
private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)

@Composable
fun CollapsingToolbar() {
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Header()
        Up {}
        Body(scroll = scrollState)
        Image {
            scrollState.value
        }
        Title {
            scrollState.value
        }
    }

}

@Composable
private fun Image(
    scrollProvider: () -> Int
) {
    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }

    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = HzPadding.then(Modifier.statusBarsPadding())
    ) {
        SnackImage(
            imageUrl = R.drawable.ic_launcher_foreground,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun SnackImage(
    @DrawableRes imageUrl: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    Surface(
        color = Color.LightGray,
        shape = CircleShape,
        modifier = modifier
    ) {
        androidx.compose.foundation.Image(
            painter = painterResource(id = imageUrl), contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val collapseFraction = collapseFractionProvider()

        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize.toDp(), imageMinSize.toDp(), collapseFraction)
        val imagePlaceable = measurables[0].measure(
            Constraints.fixed(
                imageWidth.roundToPx(),
                imageWidth.roundToPx()
            )
        )

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth.dp - imageWidth) / 2, // centered when expanded
            constraints.maxWidth.dp - imageWidth, // right aligned when collapsed
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY.plus(imageWidth.roundToPx())
        ) {
            imagePlaceable.placeRelative(imageX.roundToPx(), imageY)
        }
    }
}

@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(color = Color.Black)
    )
}

@Composable
private fun Title(scrollProvider: () -> Int) {
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()
            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(color = Color.White)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Cupcake",
            color = Color.Black,
            modifier = HzPadding
        )
        Text(
            text = "Tag",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = HzPadding
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "200",
            color = Color.Red,
            modifier = HzPadding
        )

        Spacer(Modifier.height(8.dp))
        Divider()
    }
}

@Composable
fun Body(scroll: ScrollState) {

    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(MinTitleOffset)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(Modifier.height(GradientScroll))
            Column {
                Spacer(Modifier.height(ImageOverlap))
                Spacer(Modifier.height(TitleHeight))

                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Detail Header",
                    color = Color.Black,
                    modifier = HzPadding
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    "loremn dtata"
                )

                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Detail Header",
                    color = Color.Black,
                    modifier = HzPadding
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    "loremn dtata"
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Detail Header",
                    color = Color.Black,
                    modifier = HzPadding
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    "loremn dtata"
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Detail Header",
                    color = Color.Black,
                    modifier = HzPadding
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    "loremn dtata"
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Detail Header",
                    color = Color.Black,
                    modifier = HzPadding
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    "loremn dtata"
                )
            }
        }
    }
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = Neutral8.copy(alpha = 0.32f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            tint = Color.White,
            contentDescription = ""
        )
    }
}

val Neutral8 = Color(0xff121212)