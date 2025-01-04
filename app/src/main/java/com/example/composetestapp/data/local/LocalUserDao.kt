package com.example.composetestapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
	@Query("SELECT * FROM users")
	suspend fun getUsers(): List<LocalUser>

	@Insert(onConflict = OnConflictStrategy.ABORT)
	suspend fun saveUser(localUser: LocalUser)

	@Delete
	suspend fun deleteUser(localUser: LocalUser)
}