package com.meta.littlelemon.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.meta.littlelemon.LabeledTextField

@Composable
fun UserDetails(
    firstName: MutableState<String>,
    lastName: MutableState<String>,
    email: MutableState<String>,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(10.dp)) {
        Text(
            "Personal information",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 60.dp)
        )

        LabeledTextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            placeholder = "John",
            description = "First name",
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
        )

        LabeledTextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            placeholder = "Doe",
            description = "Last name",
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
        )

        LabeledTextField(
            value = email.value,
            onValueChange = { email.value = it },
            placeholder = "example@mail.com",
            description = "Email",
            enabled = enabled,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}