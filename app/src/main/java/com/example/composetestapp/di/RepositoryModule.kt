package com.example.composetestapp.di

import com.example.composetestapp.data.UserRepositoryImpl
import com.example.composetestapp.domain.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
	@Binds
	@Singleton
	fun bindUserRepository(
		repository: UserRepositoryImpl
	): UserRepository
}