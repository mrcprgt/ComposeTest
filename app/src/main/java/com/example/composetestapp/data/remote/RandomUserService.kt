package com.example.composetestapp.data.remote

import RemoteUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserService {
	@GET("/api")
	suspend fun getUsers(@Query("results") results: Int): RemoteUserResponse
}