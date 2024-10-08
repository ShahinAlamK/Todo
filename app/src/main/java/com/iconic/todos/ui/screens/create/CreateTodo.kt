package com.iconic.todos.ui.screens.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.iconic.todos.R
import com.iconic.todos.components.Buttons
import com.iconic.todos.components.RoundIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    modifier: Modifier = Modifier,
    navigateToBack: () -> Unit,
    createViewModel: CreateViewModel = hiltViewModel(),
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
                        value = createViewModel.createTodoUiState.todo.title,
                        onValueChange = {
                            createViewModel.updateCreateTodoUiState(
                                todo = createViewModel.createTodoUiState.todo.copy(
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
                value = createViewModel.createTodoUiState.todo.description,
                onValueChange = {
                    createViewModel.updateCreateTodoUiState(
                        todo = createViewModel.createTodoUiState.todo.copy(
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
            if (createViewModel.createTodoUiState.error != null) Text(createViewModel.createTodoUiState.error!!)
            Spacer(modifier = Modifier.height(15.dp))
            Buttons(
                shapes = RoundedCornerShape(0.dp),
                enabled = createViewModel.createTodoUiState.isEnable,
                label = if (createViewModel.createTodoUiState.isLoading) "Please wait..." else "Create",
                onClick = { createViewModel.createTodo() }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

    }
}
