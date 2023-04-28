package com.meta.littlelemon

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun LabeledTextField(
    value: String,
    onValueChange: (String) -> Unit = {},
    placeholder: String,
    description: String,
    enabled: Boolean = true,
    error: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column {
        Text(
            description,
            style = MaterialTheme.typography.caption,
            color = if (error) MaterialTheme.colors.error else MaterialTheme.colors.surface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .border(
                    border = BorderStroke(
                        1.dp,
                        if (error) MaterialTheme.colors.error else MaterialTheme.colors.onSurface
                    ),
                    shape = MaterialTheme.shapes.small
                ),
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            interactionSource = interactionSource,
            enabled = enabled
        ) {
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                innerTextField = it,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                placeholder = {
                    Text(
                        placeholder,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                },
                contentPadding = TextFieldDefaults.outlinedTextFieldPadding(
                    top = 10.dp,
                    bottom = 10.dp
                ),
                enabled = enabled,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onBackground,
                    placeholderColor = MaterialTheme.colors.onSurface,
                    backgroundColor = MaterialTheme.colors.background
                ),
                interactionSource = interactionSource
            )
        }

    }
}