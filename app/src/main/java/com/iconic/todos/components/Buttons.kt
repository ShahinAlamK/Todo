package com.iconic.todos.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp



@Composable
fun Buttons(
    enabled: Boolean = true,
    isOutline:Boolean=false,
    label: String,
    color: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    height: Dp = 50.dp,
    shapes: Shape = MaterialTheme.shapes.small,
    onClick: () -> Unit
) {

    if (isOutline) {
        OutlinedButton(
            enabled = enabled,
            modifier = Modifier
            .fillMaxWidth()
            .height(height),
            shape = shapes,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = contentColor
            ),
            onClick = { onClick() }) {
            Text(text = label, style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        ElevatedButton(
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(height),
            shape = shapes,
            elevation = ButtonDefaults.elevatedButtonElevation(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = color,
                contentColor = contentColor
            ),
            onClick = { onClick() }) {
            Text(text = label, style = MaterialTheme.typography.bodyLarge)
        }
    }
}