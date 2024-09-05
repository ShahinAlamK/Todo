package com.iconic.todos.ui.screens.update

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.iconic.todos.components.Buttons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(
    modifier: Modifier = Modifier,
    navigateToBack: () -> Unit,
    updateViewModel: UpdateViewModel = hiltViewModel(),
) {

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = navigateToBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                title = {
                    TextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                        ),
                        singleLine = true,
                        placeholder = { Text(text = "Enter title") },
                        modifier = Modifier.fillMaxWidth(),
                        value = updateViewModel.updateTodoUiState.todo.title,
                        onValueChange = {
                            updateViewModel.updateCreateTodoUiState(
                                todo = updateViewModel.updateTodoUiState.todo.copy(
                                    title = it
                                )
                            )
                        },
                    )
                },

                actions = { Spacer(modifier = Modifier.size(30.dp)) }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 10.dp),
        ) {

            Spacer(modifier = Modifier.height(15.dp))

            TextField(
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                ),
                modifier = Modifier.weight(1f),
                value = updateViewModel.updateTodoUiState.todo.description,
                onValueChange = {
                    updateViewModel.updateCreateTodoUiState(
                        todo = updateViewModel.updateTodoUiState.todo.copy(
                            description = it
                        )
                    )
                },
                placeholder = {
                    Text(
                        text = "Description", style = MaterialTheme.typography.bodyMedium
                    )
                })


            Spacer(modifier = Modifier.height(15.dp))
            if (updateViewModel.updateTodoUiState.error != null) Text(updateViewModel.updateTodoUiState.error!!)
            Spacer(modifier = Modifier.height(15.dp))
            Buttons(
                shapes = RoundedCornerShape(0.dp),
                enabled = updateViewModel.updateTodoUiState.isEnable,
                label = if (updateViewModel.updateTodoUiState.isLoading) "Please wait..." else "Update",
                onClick = { updateViewModel.updateTodo() }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

    }
}
