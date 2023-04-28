package com.meta.littlelemon.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.meta.littlelemon.components.Button
import com.meta.littlelemon.components.Header
import com.meta.littlelemon.components.UserDetails
import com.meta.littlelemon.ui.theme.LittleLemonTheme
import com.meta.littlelemon.util.LocalUserRepository
import com.meta.littlelemon.util.navigate
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun Profile(navController: NavController) {
    val userRepository = LocalUserRepository.current
    val user by userRepository.user.observeAsState()
    val scope = LocalLifecycleOwner.current.lifecycleScope

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())
    ) {
        Header()

        Box(modifier = Modifier.weight(1f))

        UserDetails(
            firstName = mutableStateOf(user?.firstName ?: ""),
            lastName = mutableStateOf(user?.lastName ?: ""),
            email = mutableStateOf(user?.email ?: ""),
            enabled = false
        )

        Box(modifier = Modifier.weight(1f))

        Button(
            text = "Log out",
            onClick = {
                scope.launch {
                    navController.navigate(Destination.Onboarding, clearBackStack = true)
                    userRepository.save(user = null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    LittleLemonTheme {
        Profile(rememberNavController())
    }
}