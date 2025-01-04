package com.example.composetestapp.data

import android.database.sqlite.SQLiteConstraintException
import com.example.composetestapp.data.local.UserDao
import com.example.composetestapp.data.remote.RandomUserService
import com.example.composetestapp.domain.User
import com.example.composetestapp.domain.UserRepository
import com.example.composetestapp.util.CustomException
import com.example.composetestapp.util.Resource
import com.example.composetestapp.util.toLocalUser
import com.example.composetestapp.util.toUser
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
	private val userDao: UserDao,
	private val userService: RandomUserService
) : UserRepository {

	override suspend fun getUsers(): Resource<List<User>> {
		return try {
			Resource.Success(userDao.getUsers().map { it.toUser() })
		} catch (e: Exception) {
			Resource.Error(e.message ?: e.localizedMessage)
		}
	}

	override suspend fun saveUser(user: User) {
		try {
			userDao.saveUser(user.toLocalUser())
		} catch (e: SQLiteConstraintException) {
			throw CustomException("User is already in favorites.")
		}
	}

	override suspend fun deleteUser(user: User) {
		userDao.deleteUser(user.toLocalUser())
	}

	override suspend fun fetchUsers(number: Int): Resource<List<User>> {
		return try {
			Resource.Success(userService.getUsers(number).results.map {
				it.toUser()
			})
		} catch (e: Exception) {
			Resource.Error(message = e.message ?: e.localizedMessage)
		}
	}
}