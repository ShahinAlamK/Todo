package com.iconic.todos.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.iconic.todos.R
import com.iconic.todos.components.Buttons
import com.iconic.todos.components.CustomField
import com.iconic.todos.ui.components.LoadingDialog


@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = viewModel.autStatus) {
        if (viewModel.autStatus == AutStatus.Authenticated) {
            navigateToHome()
        }
    }

    if (viewModel.loginUiState.isLoading) {
        LoadingDialog(msg = "Please wait...")
    }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .fillMaxSize()
            .padding(horizontal = 30.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Login", style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Spacer(modifier = Modifier.height(90.dp))

        //Email Input
        CustomField(
            value = viewModel.loginUiState.email,
            onValueChange = {
                viewModel.updateState(viewModel.loginUiState.copy(email = it))
            },
            placeholder = "Enter Email",
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = ""
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        //Passwords Input
        CustomField(
            value = viewModel.loginUiState.password,
            onValueChange = { viewModel.updateState(viewModel.loginUiState.copy(password = it)) },
            placeholder = "Passwords",
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation = if (viewModel.loginUiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = ""
                )
            },
            trailingIcon = {
                IconButton(onClick = { viewModel.passwordVisible() }) {
                    Icon(
                        painter = painterResource(id = if (viewModel.loginUiState.isPasswordVisible) R.drawable.visibility_off else R.drawable.visibility),
                        contentDescription = ""
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(70.dp))

        Buttons(
            enabled = viewModel.loginUiState.isEnable,
            label = "Login",
            onClick = { viewModel.login() }
        )

        Spacer(modifier = Modifier.height(30.dp))
        Buttons(
            isOutline = true,
            label = "Create a account",
            onClick = navigateToRegister
        )
        Spacer(modifier = Modifier.height(20.dp))

        if (viewModel.loginUiState.error != null) Text(
            text = viewModel.loginUiState.error!!,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.error
            )
        )
        Spacer(modifier = Modifier.height(50.dp))
    }


}

