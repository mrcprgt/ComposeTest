package com.example.composetestapp.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.SubcomposeAsyncImage
import com.example.composetestapp.domain.User
import com.example.composetestapp.util.Helpers.capitalize

@Composable
fun UserDetails(
	navController: NavController,
	user: User,
	onClick: () -> Unit
) {
	val scrollState = rememberScrollState()

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
			.verticalScroll(scrollState)
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			if (user.isFavorite) {
				IconButton(
					onClick = {
						onClick.invoke()
						navController.popBackStack()
					}
				) {
					Icon(Icons.Default.Delete, contentDescription = "Delete")
				}
			} else {
				IconButton(
					onClick = { onClick.invoke() }
				) {
					Icon(Icons.Default.Star, contentDescription = "Save")
				}
			}
			IconButton(
				onClick = { navController.popBackStack() }) {
				Icon(Icons.Default.Close, contentDescription = "Back")
			}
		}
		SubcomposeAsyncImage(
			model = user.picture.large,
			contentDescription = null,
			modifier = Modifier
				.size(320.dp)
				.align(Alignment.CenterHorizontally)
				.border(
					1.5.dp, MaterialTheme.colorScheme.primary
				),
			loading = {
				CircularProgressIndicator()
			}
		)
		Spacer(Modifier.size(8.dp))
		Text(
			text = user.name,
			fontSize = 24.sp,
			fontWeight = FontWeight.Bold
		)

		UserDetailItem(icon = Icons.Default.Face, label = "Gender", value = user.gender.capitalize())
		UserDetailItem(icon = Icons.Default.LocationOn, label = "Location", value = user.location)
		UserDetailItem(icon = Icons.Default.Email, label = "Email", value = user.email)
		UserDetailItem(icon = Icons.Default.DateRange, label = "Birthday", value = "${user.birthday} - ${user.age} years old")
		UserDetailItem(icon = Icons.Default.Person, label = "Nationality", value = user.nationality)
		UserDetailItem(icon = Icons.Default.Phone, label = "Phone", value = user.phone)
		UserDetailItem(icon = Icons.Default.Phone, label = "Cell", value = user.cell)
	}
}