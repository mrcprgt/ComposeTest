package com.example.composetestapp

import android.app.Application
import androidx.room.Database
import androidx.room.RoomDatabase
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import com.example.composetestapp.data.local.LocalUser
import com.example.composetestapp.data.local.UserDao
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), SingletonImageLoader.Factory {

	@Inject
	lateinit var imageLoader: ImageLoader

	override fun newImageLoader(context: PlatformContext) = imageLoader

}

