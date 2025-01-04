package com.example.composetestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetestapp.ui.CustomTabLayout
import com.example.composetestapp.ui.NumberInputDialog
import com.example.composetestapp.ui.UserDetails
import com.example.composetestapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MyApp()
		}
	}
}

@Composable
fun MyApp(mainViewModel: MainViewModel = hiltViewModel()) {
	val navController = rememberNavController()
	val focusRequester = remember { FocusRequester() }

	val isShowDialog by mainViewModel.isShowDialog.collectAsState()
	val randomizedUsersState by mainViewModel.randomizedUsers.collectAsState()
	val favoriteUsersState by mainViewModel.favoriteUsers.collectAsState()
	val isRefreshing by mainViewModel.isRefreshing.collectAsState()

	val snackBarState = remember { SnackbarHostState() }
	var selectedTabIndex by remember { mutableIntStateOf(0) }

	LaunchedEffect(Unit) {
		mainViewModel.snackBarMessageFlow.collectLatest { message ->
			snackBarState.showSnackbar(message)
		}
	}

	Scaffold(
		snackbarHost = { SnackbarHost((snackBarState)) }
	) { _ ->
		NavHost(navController, startDestination = "list") {
			composable("list") {
				Column(
					modifier = Modifier
						.fillMaxSize()
						.padding(8.dp),
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					if (isShowDialog) {
						NumberInputDialog(focusRequester = focusRequester,
							onPositiveClick = { number ->
								mainViewModel.fetchUsers(number)
							},
							onNegativeClick = {

							})
					}
					when (randomizedUsersState) {
						is Resource.Loading -> {
							CircularProgressIndicator()
						}

						is Resource.Success -> {
							randomizedUsersState.data?.let {
								CustomTabLayout(
									navController = navController,
									randomUsers = it,
									favoriteUsers = favoriteUsersState.data,
									isRefreshing = isRefreshing,
									selectedTabIndex = selectedTabIndex,
									onRefresh = {
										mainViewModel.refresh()
									},
									onSelectFavorites = {
										mainViewModel.getUsers()
									},
									onTabSelected = { index ->
										selectedTabIndex = index
									}
								)
							}
						}

						is Resource.Error -> {
							val errorMessage = (randomizedUsersState as Resource.Error).message
							Text("Error: $errorMessage")
						}
					}
				}
			}
			composable("detail/{id}") { navBackStackEntry ->
				val userId = navBackStackEntry.arguments?.getString("id")

				// Check if is is in random list or favorite list
				val user = favoriteUsersState.data?.let {
					it.find {
						it.id == userId
					}
				} ?: randomizedUsersState.data?.let {
					it.find {
						it.id == userId
					}
				}

				user?.let { user ->
					UserDetails(navController, user, onClick = {
						if (user.isFavorite) {
							mainViewModel.deleteUser(user)
						} else {
							mainViewModel.saveUser(user)
						}
					})
				}
			}
		}
	}
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	MyApp()
}