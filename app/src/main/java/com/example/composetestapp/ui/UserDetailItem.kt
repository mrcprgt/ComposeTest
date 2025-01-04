package com.example.composetestapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun UserDetailItem(icon: ImageVector, label: String, value: String) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier.padding(vertical = 8.dp)
	) {
		Icon(icon, contentDescription = label, tint = Color.Gray, modifier = Modifier.size(24.dp))
		Spacer(modifier = Modifier.width(16.dp))
		Column {
			Text(text = label, fontWeight = FontWeight.Bold)
			Text(text = value)
		}
	}
}