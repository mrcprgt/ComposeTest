package com.example.composetestapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.composetestapp.domain.User

@Composable
fun CustomTabLayout(
	navController: NavController,
	randomUsers: List<User>,
	favoriteUsers: List<User>? = null,
	isRefreshing: Boolean,
	onRefresh: () -> Unit,
	onSelectFavorites: () -> Unit,
	selectedTabIndex: Int,
	onTabSelected: (Int) -> Unit,
) {
	val tabs = listOf("Randomizer", "Favorites")

	Column(
		modifier = Modifier
			.fillMaxSize()
	) {
		TabRow(selectedTabIndex = selectedTabIndex) {
			tabs.forEachIndexed { index, title ->
				Tab(
					text = { Text(title) },
					selected = selectedTabIndex == index,
					onClick = { onTabSelected(index) }
				)
			}
		}
		when (selectedTabIndex) {
			0 -> {
				RandomizedUserList(navController, randomUsers, isRefreshing, onRefresh)
			}

			1 -> {
				onSelectFavorites.invoke()
				SavedUserList(navController, favoriteUsers)
			}
		}
	}
}
