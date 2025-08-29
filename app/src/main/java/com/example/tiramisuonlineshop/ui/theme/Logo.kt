package com.example.tiramisuonlineshop.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tiramisuonlineshop.R

@Composable
fun PortraitLogo() {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val screenWidth = configuration.screenWidthDp.dp

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_round),
                contentDescription = "App Logo",
                modifier = Modifier
                    .width(screenWidth * 0.7f)   
                    .height(screenWidth * 0.7f)  
            )
        }
    }
}


