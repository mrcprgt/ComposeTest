package com.example.composetestapp.di

import android.content.Context
import coil3.ImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import com.example.composetestapp.data.remote.RandomUserService
import com.example.composetestapp.util.UnsafeOkHttpClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Provides
	@Singleton
	fun provideGson(): Gson = GsonBuilder()
		.setLenient()
		.create()

	@Provides
	@Singleton
	fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
		.client(provideUnsafeOkHttpClient())
		.baseUrl("https://randomuser.me")
		.addConverterFactory(GsonConverterFactory.create(gson))
		.build()

	@Provides
	@Singleton
	fun provideUnsafeOkHttpClient(): OkHttpClient {
		return UnsafeOkHttpClient.getClient()
	}

	@Provides
	@Singleton
	fun provideImageLoader(
		okHttpClient: OkHttpClient,
		@ApplicationContext context: Context
	): ImageLoader {
		return ImageLoader.Builder(context)
			.components{
				add(
					OkHttpNetworkFetcherFactory (
						okHttpClient
					)
				)
			}
			.crossfade(true)
			.build()
	}

	@Provides
	@Singleton
	fun provideRandomUserService(retrofit: Retrofit): RandomUserService =
		retrofit.create(RandomUserService::class.java)
}