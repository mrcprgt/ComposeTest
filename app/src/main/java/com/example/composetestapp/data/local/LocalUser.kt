package com.example.composetestapp.data.local

import androidx.room.*

@Entity(tableName = "users")
data class LocalUser(
	@PrimaryKey val uuid: String,
	val gender: String,
	val name: String,
	val location: String,
	val email: String,
	val birthday: String,
	val phone: String,
	val cell: String,
	@Embedded val picture: Picture,
	val nationality: String,
	val age: Int,
	val isFavorite: Boolean = true
)

data class Picture(
	val large: String,
	val thumbnail: String
)
