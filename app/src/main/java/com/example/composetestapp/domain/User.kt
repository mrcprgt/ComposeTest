package com.example.composetestapp.domain

data class User(
	val id: String,
	val name: String,
	val gender: String,
	val location: String,
	val birthday: String,
	val age: Int,
	val phone: String,
	val cell: String,
	val picture: Picture,
	val nationality: String,
	val email: String,
	val isFavorite: Boolean = false
)

data class Picture(
	val thumb: String,
	val large: String
)