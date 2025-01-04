package com.example.composetestapp.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.example.composetestapp.domain.User

@Composable
fun UserCard(user: User, onClick: () -> Unit) {
	Card(
		modifier = Modifier
			.padding(8.dp)
			.fillMaxWidth()
			.clickable(onClick = onClick),
		shape = RoundedCornerShape(8.dp),
		elevation = CardDefaults.cardElevation(),
	) {
		Column(
			modifier = Modifier.padding(16.dp)
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				SubcomposeAsyncImage(
					model = user.picture.thumb,
					contentDescription = null,
					modifier = Modifier
						.size(50.dp)
						.clip(CircleShape)
						.border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape),
					loading = {
						CircularProgressIndicator()
					}

				)
				Spacer(modifier = Modifier.width(16.dp))
				Column {
					Text(text = user.name, style = MaterialTheme.typography.titleMedium)
					Text(
						text = user.location,
						style = MaterialTheme.typography.bodySmall
					)
				}
			}

		}
	}
}