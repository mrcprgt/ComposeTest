package com.example.composetestapp.util

import Location
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object Helpers {
	fun formatUtcToReadable(utcDate: String): String {
		val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
		inputFormat.timeZone = TimeZone.getTimeZone("UTC")
		val date = inputFormat.parse(utcDate)

		val outputFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
		return outputFormat.format(date)
	}

	fun String.capitalize(): String {
		return this.replaceFirstChar {
			if (it.isLowerCase()) it.titlecase(
				Locale.getDefault()
			) else it.toString()
		}
	}

	fun Location.toReadableString(): String {
		return with (this) {
			"""
				${street.number} ${street.name} ${city}, ${state}, $country $postcode
			""".trimIndent()
		}
	}
}