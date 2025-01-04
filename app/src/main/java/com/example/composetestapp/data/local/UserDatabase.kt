package com.example.composetestapp.data.local

import android.content.Context
import android.service.autofill.UserData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocalUser::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
	abstract fun userDao(): UserDao
}