package com.example.composetestapp.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.example.composetestapp.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun NumberInputDialog(
	focusRequester: FocusRequester,
	onPositiveClick: (number: Int) -> Unit,
	onNegativeClick: () -> Unit
) {
	var number by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("1")) }
	var isError by remember { mutableStateOf(false) }

	LaunchedEffect(Unit) {
		delay(300) // Optional delay to ensure the dialog is fully visible
		focusRequester.requestFocus()
		number = number.copy(selection = TextRange(number.text.length))
	}

	AlertDialog(
		onDismissRequest = {},
		title = { Text("Enter the number of users") },
		text = {
			OutlinedTextField(
				value = number,
				onValueChange = { newValue ->
					val filteredValue = newValue.text.filter { it.isDigit() }
					val intValue = filteredValue.toIntOrNull()

					// Allow empty state but default to 1 if empty
					if (filteredValue.isEmpty()) {
						number = TextFieldValue(
							text = "",
							selection = newValue.selection
						)
						isError = true
					} else if (intValue != null && intValue in 1..5000) {
						number = TextFieldValue(
							text = filteredValue,
							selection = newValue.selection
						)
						isError = false
					}
				},
				isError = isError,
				label = { Text("Enter a number (1-5000)") },
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(focusRequester)
			)
		},
		confirmButton = {
			Button(
				colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
				enabled = !isError,
				onClick = { onPositiveClick.invoke(number.text.toInt()) }
			) {
				Text("Generate")
			}
		},
		dismissButton = {
			Button(
				colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
				onClick = {
					onNegativeClick.invoke()
				}
			) {
				Text("Exit")
			}
		}
	)
}