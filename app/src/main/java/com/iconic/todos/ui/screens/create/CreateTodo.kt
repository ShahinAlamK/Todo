package com.iconic.todos.ui.screens.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import com.iconic.todos.R
import com.iconic.todos.components.Buttons
import com.iconic.todos.components.RoundIcon

@Composable
fun CreateScreen(
    modifier: Modifier = Modifier,
    createViewModel: CreateViewModel,
    isOpen: Boolean,
    cancel: () -> Unit,
    create: () -> Unit
) {

    val colorList =
        listOf(Color(0xFF1F90D3), Color(0xFFF04712), Color(0xFFEC05AF), Color(0xFF31DD47))

    var selectColor by remember { mutableIntStateOf(0) }

    if (isOpen)
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            textContentColor = MaterialTheme.colorScheme.onBackground,
            tonalElevation = 6.dp,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = {},
            confirmButton = { /*TODO*/ },

            title = {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.create))

                    RoundIcon(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_clear),
                                contentDescription = ""
                            )
                        },
                        onClick = cancel
                    )
                }
            },
            text = {
                Column {
                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        value = createViewModel.createTodoUiState.todo.title,
                        onValueChange = {
                            createViewModel.updateCreateTodoUiState(
                                todo = createViewModel.createTodoUiState.todo.copy(
                                    title = it
                                )
                            )
                        },
                        placeholder = {
                            Text(
                                text = "Enter Title",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
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
                                text = "Description",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(colorList.size) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(colorList[it])
                                    .padding(10.dp)
                                    .clickable {
                                        selectColor = it
                                        createViewModel.updateCreateTodoUiState(
                                            todo = createViewModel.createTodoUiState.todo.copy(
                                                color = it
                                            )
                                        )
                                    }
                            ) {
                                if (selectColor == it) {
                                    Icon(
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                        painter = painterResource(id = R.drawable.ic_check),
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))
                    if (createViewModel.createTodoUiState.error != null) Text(createViewModel.createTodoUiState.error!!)
                    Spacer(modifier = Modifier.height(15.dp))
                    Buttons(
                        enabled = createViewModel.createTodoUiState.isEnable,
                        label = if (createViewModel.createTodoUiState.isLoading) "Creating..." else "Create",
                        onClick = create
                    )
                }
            }
        )
}
