package com.nameisjayant.composebootcampmaterial3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.nameisjayant.composebootcampmaterial3.components.CollapsingToolbar
import com.nameisjayant.composebootcampmaterial3.components.CustomLayoutScreen
import com.nameisjayant.composebootcampmaterial3.components.FlowRowAndColumn
import com.nameisjayant.composebootcampmaterial3.ui.theme.ComposeBootcampMaterial3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBootcampMaterial3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CustomLayoutScreen()
                }
            }
        }
    }
}
