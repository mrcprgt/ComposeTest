package com.example.composetestapp.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composetestapp.domain.User


@Composable
fun SavedUserList(
	navController: NavController,
	users: List<User>?
) {
	if (users == null) {
		Text(text = "No favorites yet. Please favorite one from the randomized list.")
	} else {
		LazyColumn(
			modifier = Modifier.fillMaxSize(),
			contentPadding = PaddingValues(8.dp)
		) {
			items(users) { user ->
				UserCard(user) {
					navController.navigate("detail/${user.id}")
				}
			}
		}
	}
}
