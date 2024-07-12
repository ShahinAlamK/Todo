package com.iconic.todos.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.iconic.todos.R

@Composable
fun WallpaperBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .blur(8.dp)
            .then(modifier)
    ) {
        Image(
            modifier = Modifier
                .fillMaxHeight(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.kinetic_avenue),
            contentDescription = null
        )
        content()
    }
}

