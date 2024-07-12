package com.iconic.todos.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.iconic.todos.R
import com.iconic.todos.components.DeleteTodo
import com.iconic.todos.components.RoundIcon
import com.iconic.todos.components.TodoCard
import com.iconic.todos.ui.components.CommonLoading
import com.iconic.todos.ui.components.EmptyScreen
import com.iconic.todos.ui.components.ErrorScreen
import com.iconic.todos.ui.screens.account.ProfileViewModel
import com.iconic.todos.ui.screens.create.CreateScreen
import com.iconic.todos.ui.screens.create.CreateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToProfile: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    createViewModel: CreateViewModel = hiltViewModel()
) {

    CreateScreen(
        createViewModel = createViewModel,
        isOpen = createViewModel.createTodoUiState.openDialog,
        cancel = { createViewModel.openDialog() },
        create = { createViewModel.createTodo() }
    )

    DeleteTodo(
        isDelete = homeViewModel.homeUiState.delete,
        cancel = { homeViewModel.deleteToggles() },
        isLoading = homeViewModel.homeUiState.isDelete,
        delete = {
            homeViewModel.deleteTodo()
            homeViewModel.deleteToggles()
        },
    )


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                navigationIcon = {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(viewModel.profileUiState.userModel.profile).crossfade(true)
                            .build(),
                        modifier = Modifier
                            .padding(8.dp)
                            .size(35.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable { navigateToProfile() },
                        error = painterResource(R.drawable.placeholder),
                        placeholder = painterResource(R.drawable.placeholder),
                        contentScale = ContentScale.Crop,
                        contentDescription = ""
                    )
                },
                actions = {

                    RoundIcon(color = MaterialTheme.colorScheme.primary, icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = ""
                        )
                    },
                        onClick = {
                            homeViewModel.deleteAllTodo()
                        })
                    Spacer(modifier = Modifier.size(20.dp))

                    RoundIcon(color = MaterialTheme.colorScheme.primary, icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = ""
                        )
                    },
                        onClick = { createViewModel.openDialog() })

                    Spacer(modifier = Modifier.size(10.dp))
                })
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 5.dp)
        ) {
            if (homeViewModel.homeUiState.isLoading) {
                CommonLoading()
            } else if (homeViewModel.homeUiState.error != null) {
                ErrorScreen(msg = homeViewModel.homeUiState.error!!)
            } else {
                if (homeViewModel.homeUiState.todos.isEmpty()) {
                    EmptyScreen()
                } else {
                    TodoList(
                        viewModel = homeViewModel,
                        homeUiState = homeViewModel.homeUiState
                    )
                }
            }

        }
    }


}

@Composable
fun TodoList(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
    viewModel: HomeViewModel
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .then(modifier),
    ) {
        items(homeUiState.todos.size) {
            Spacer(modifier = Modifier.height(10.dp))
            TodoCard(
                index = it + 1,
                todo = homeUiState.todos[it],
                delete = { todo ->
                    viewModel.setDelete(todo)
                    viewModel.deleteToggles()
                }
            )
        }
    }
}




