package com.example.composetestapp.di

import android.content.Context
import androidx.room.Room
import com.example.composetestapp.data.UserRepositoryImpl
import com.example.composetestapp.data.local.UserDao
import com.example.composetestapp.data.local.UserDatabase
import com.example.composetestapp.data.remote.RandomUserService
import com.example.composetestapp.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase {
		return Room.databaseBuilder(
			context,
			UserDatabase::class.java,
			"user_db"
		).build()
	}

	@Provides
	@Singleton
	fun provideUserDao(database: UserDatabase): UserDao {
		return database.userDao()
	}
}