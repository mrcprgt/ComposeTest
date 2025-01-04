package com.example.composetestapp

import com.example.composetestapp.domain.Picture
import com.example.composetestapp.domain.User

object DummyData {
	val users = listOf(
		User(
			id = "1",
			name = "John Doe",
			gender = "Male",
			location = "New York, USA",
			birthday = "1990-01-01",
			age = 35,
			phone = "123-456-7890",
			cell = "098-765-4321",
			picture = Picture(
				thumb = "https://example.com/thumb1.jpg",
				large = "https://example.com/large1.jpg"
			),
			nationality = "American",
			email = "john.doe@example.com",
			isFavorite = false
		),
		User(
			id = "2",
			name = "Jane Smith",
			gender = "Female",
			location = "London, UK",
			birthday = "1985-05-15",
			age = 40,
			phone = "234-567-8901",
			cell = "987-654-3210",
			picture = Picture(
				thumb = "https://example.com/thumb2.jpg",
				large = "https://example.com/large2.jpg"
			),
			nationality = "British",
			email = "jane.smith@example.com",
			isFavorite = true
		),
		User(
			id = "3",
			name = "Alice Johnson",
			gender = "Female",
			location = "Sydney, Australia",
			birthday = "1995-07-20",
			age = 30,
			phone = "345-678-9012",
			cell = "876-543-2109",
			picture = Picture(
				thumb = "https://example.com/thumb3.jpg",
				large = "https://example.com/large3.jpg"
			),
			nationality = "Australian",
			email = "alice.johnson@example.com",
			isFavorite = false
		)
	)
}
