package com.meta.littlelemon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.meta.littlelemon.components.Button
import com.meta.littlelemon.components.Header
import com.meta.littlelemon.components.UserDetails
import com.meta.littlelemon.screens.Destination
import com.meta.littlelemon.ui.theme.LittleLemonTheme
import com.meta.littlelemon.util.LocalUserRepository
import com.meta.littlelemon.util.User
import com.meta.littlelemon.util.navigate
import kotlinx.coroutines.launch

@Composable
fun Onboarding(navController: NavController) {
    val userRepository = LocalUserRepository.current
    var firstName = rememberSaveable { mutableStateOf("") }
    var lastName = rememberSaveable { mutableStateOf("") }
    var email = rememberSaveable { mutableStateOf("") }
    val scope = LocalLifecycleOwner.current.lifecycleScope

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())
    ) {
        Header()

        WelcomeBanner()

        UserDetails(
            firstName = firstName,
            lastName = lastName,
            email = email,
            modifier = Modifier.padding(top = 30.dp)
        )

        Button(
            text = "Register",
            onClick = {
                scope.launch {
                    navController.navigate(Destination.Home, clearBackStack = true)
                    val user = User(firstName.value, lastName.value, email.value)
                    userRepository.save(user)
                }
            },
            enabled = listOf(firstName, lastName, email).all { it.value.isNotBlank() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}

@Composable
private fun WelcomeBanner() {
    Surface(color = MaterialTheme.colors.primary, modifier = Modifier.fillMaxWidth()) {
        Text(
            "Let's get to know you",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(40.dp)
        )
    }
}

@Composable
@Preview
private fun Preview() {
    LittleLemonTheme {
        Onboarding(rememberNavController())
    }
}