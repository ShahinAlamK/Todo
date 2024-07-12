package com.iconic.todos.ui.screens.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.iconic.todos.R
import com.iconic.todos.components.Buttons
import com.iconic.todos.components.CustomField
import com.iconic.todos.ui.components.ErrorScreen
import com.iconic.todos.ui.components.LoadingDialog
import com.iconic.todos.ui.screens.home.HomeViewModel
import com.iconic.todos.utils.dateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateToBack: () -> Unit,
    logOut: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
    todoViewModel: HomeViewModel = hiltViewModel()
) {

    if (viewModel.profileUiState.isLoading) {
        CircularProgressIndicator()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                ),


                navigationIcon = {
                    IconButton(onClick = navigateToBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = ""
                        )
                    }
                },

                actions = {
                    IconButton(onClick = {
                        viewModel.logOut()
                        logOut()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = ""
                        )
                    }
                },

                title = { Text(text = "Profile") })

        }

    ) { paddingValues ->
        if (viewModel.profileUiState.error != null)
            ErrorScreen(msg = viewModel.profileUiState.error!!)
        else
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.size(30.dp))


                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(viewModel.profileUiState.userModel.profile)
                        .crossfade(true)
                        .build(),
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    error = painterResource(R.drawable.placeholder),
                    placeholder = painterResource(R.drawable.placeholder),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )

                Spacer(modifier = Modifier.size(20.dp))
                CustomField(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_person),
                            contentDescription = ""
                        )
                    },
                    value = viewModel.profileUiState.userModel.username,
                    onValueChange = {
                        viewModel.updateState(
                            value = viewModel.profileUiState.userModel.copy(username = it)
                        )
                    },
                    placeholder = "Username"
                )
                Column {
                    Spacer(modifier = Modifier.size(20.dp))
                    Text(text = "UID : ${viewModel.profileUiState.userModel.uid}")
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = "Email : ${viewModel.profileUiState.userModel.email}")
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = "Total Todo : ${todoViewModel.homeUiState.todos.size}")
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = "Create : ${dateFormat(viewModel.profileUiState.userModel.date)}")
                }

                Spacer(modifier = Modifier.size(30.dp))
                Buttons(
                    label = if (viewModel.profileUiState.isUpdate) "Updating..." else "Update",
                    onClick = {
                        viewModel.updateProfile()
                    })
            }
    }
}