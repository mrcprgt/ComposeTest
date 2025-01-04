package com.example.composetestapp.util

import RemoteUser
import com.example.composetestapp.data.local.LocalUser
import com.example.composetestapp.domain.Picture
import com.example.composetestapp.domain.User
import com.example.composetestapp.util.Helpers.toReadableString

fun RemoteUser.toUser() = with(this) {
	User(
		id = login.uuid,
		name = "${name.title} ${name.first} ${name.last}",
		location = location.toReadableString(),
		birthday = Helpers.formatUtcToReadable(dob.date),
		age = dob.age,
		phone = phone,
		cell = cell,
		picture = Picture(
			thumb = picture.thumbnail,
			large = picture.large
		),
		nationality = nat,
		gender = gender,
		email = email,
		isFavorite = false
	)
}

fun User.toLocalUser() = with(this) {
	LocalUser(
		uuid = id,
		gender = gender,
		name = name,
		location = location,
		email = email,
		birthday = birthday,
		phone = phone,
		cell = cell,
		picture = com.example.composetestapp.data.local.Picture(
			large = picture.large,
			thumbnail = picture.thumb
		),
		nationality = nationality,
		age = age,
		isFavorite = true
	)
}

fun LocalUser.toUser() = with(this) {
	User(
		id = uuid,
		name = name,
		location = location,
		birthday = birthday,
		age = age,
		phone = phone,
		cell = cell,
		picture = Picture(
			thumb = picture.thumbnail,
			large = picture.large
		),
		nationality = nationality,
		gender = gender,
		email = email,
		isFavorite = isFavorite
	)
}