package com.example.composetestapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetestapp.domain.User
import com.example.composetestapp.domain.UserRepository
import com.example.composetestapp.util.CustomException
import com.example.composetestapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val repository: UserRepository
) : ViewModel() {

	private val _randomizedUsers = MutableStateFlow<Resource<List<User>>>(Resource.Success(emptyList()))
	val randomizedUsers: StateFlow<Resource<List<User>>> = _randomizedUsers

	private val _favoriteUsers = MutableStateFlow<Resource<MutableList<User>>>(Resource.Success(mutableListOf()))
	val favoriteUsers: StateFlow<Resource<MutableList<User>>> = _favoriteUsers

	private val _isRefreshing = MutableStateFlow(false)
	val isRefreshing: StateFlow<Boolean> = _isRefreshing

	private val _isShowDialog = MutableStateFlow(true)
	val isShowDialog: StateFlow<Boolean> = _isShowDialog

	private val _snackBarMessageChannel = Channel<String>(
		Channel.Factory.BUFFERED
	)
	val snackBarMessageFlow = _snackBarMessageChannel.receiveAsFlow()

	fun fetchUsers(number: Int) {
		_isShowDialog.value = false
		_randomizedUsers.value = Resource.Loading(true)
		viewModelScope.launch {
			val response = repository.fetchUsers(number)
			_randomizedUsers.value = Resource.Loading(false)
			_randomizedUsers.value = response
		}
	}

	fun refresh() {
		viewModelScope.launch {
			_isRefreshing.value = true
			delay(1000)
			_isRefreshing.value = false
			_isShowDialog.value = true
		}
	}

	fun saveUser(user: User) {
		viewModelScope.launch {
			try {
				repository.saveUser(user)
				_snackBarMessageChannel.send("User added to favorites.")
			} catch (e: Exception) {
				_snackBarMessageChannel.send(
					if (e is CustomException) {
						"User already in favorites."
					} else {
						e.message ?: e.localizedMessage
					}
				)
			}
		}
	}

	fun deleteUser(user: User) {
		viewModelScope.launch {
			try {
				repository.deleteUser(user)
				_favoriteUsers.value.data?.remove(user)
				_snackBarMessageChannel.send("User deleted from favorites.")
			} catch (e: Exception) {
				_snackBarMessageChannel.send(
					if (e is CustomException) {
						"User already deleted from favorites."
					} else {
						e.message ?: e.localizedMessage
					}
				)
			}
		}
	}

	fun getUsers() {
		viewModelScope.launch {
			val users = repository.getUsers()
			_favoriteUsers.value = Resource.Success(users.data?.toMutableList())
		}
	}
}