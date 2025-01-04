package com.example.composetestapp.domain

import com.example.composetestapp.util.Resource

interface UserRepository {
	suspend fun getUsers(): Resource<List<User>>
	suspend fun saveUser(user: User)
	suspend fun deleteUser(user: User)
	suspend fun fetchUsers(number: Int): Resource<List<User>>
}