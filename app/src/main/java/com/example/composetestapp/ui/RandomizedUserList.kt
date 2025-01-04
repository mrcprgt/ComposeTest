package com.example.composetestapp.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composetestapp.domain.User
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
fun RandomizedUserList(
	navController: NavController,
	users: List<User>,
	isRefreshing: Boolean,
	onRefresh: () -> Unit
) {
	SwipeRefresh(
		state = SwipeRefreshState(isRefreshing),
		onRefresh = onRefresh
	) {
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
