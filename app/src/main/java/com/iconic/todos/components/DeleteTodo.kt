package com.iconic.todos.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DeleteTodo(
    isDelete: Boolean,
    isLoading: Boolean,
    cancel: () -> Unit,
    delete: () -> Unit
) {
    if (isDelete)
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            textContentColor = MaterialTheme.colorScheme.onBackground,
            tonalElevation = 6.dp,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            onDismissRequest = { /*TODO*/ },
            dismissButton = {
                TextButton(
                    shape = MaterialTheme.shapes.small,
                    onClick = cancel
                ) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                TextButton(
                    shape = MaterialTheme.shapes.small,
                    onClick = delete
                ) {
                    Text(text =if (isLoading) "Deleting..." else "Delete")
                }
            },
            title = { Text(text = "Warning", style = MaterialTheme.typography.bodyLarge) },
            text = {
                Text(
                    text = "Are you sure permanently deleted todo",
                    style = MaterialTheme.typography.bodyMedium
                )
            },

            )
}