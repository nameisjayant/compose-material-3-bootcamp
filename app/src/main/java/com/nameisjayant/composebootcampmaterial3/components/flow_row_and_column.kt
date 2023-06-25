package com.nameisjayant.composebootcampmaterial3.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowAndColumn() {

    FlowRow(
        modifier = Modifier.fillMaxSize(),
        maxItemsInEachRow = 3,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(20){
            Text(text = "Text $it", modifier = Modifier.padding(start = 10.dp))
        }
    }

    FlowColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        repeat(50){
            Text(text = "Text $it", modifier = Modifier.padding(vertical = 10.dp))
        }

    }


}