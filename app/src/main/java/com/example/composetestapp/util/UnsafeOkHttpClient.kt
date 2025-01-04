package com.example.composetestapp.util

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException

object UnsafeOkHttpClient {
	fun getClient() : OkHttpClient {
		return try {
			// Create a trust manager that does not validate certificate chains
			val trustAllCerts = arrayOf<TrustManager>(
				object : X509TrustManager {
					@Throws(CertificateException::class)
					override fun checkClientTrusted(
						chain: Array<X509Certificate?>?,
						authType: String?
					) {
					}

					@Throws(CertificateException::class)
					override fun checkServerTrusted(
						chain: Array<X509Certificate?>?,
						authType: String?
					) {
					}

					override fun getAcceptedIssuers(): Array<X509Certificate?>? {
						return arrayOf()
					}
				}
			)

			// Install the all-trusting trust manager
			val sslContext = SSLContext.getInstance("SSL")
			sslContext.init(null, trustAllCerts, SecureRandom())
			// Create an ssl socket factory with our all-trusting manager
			val sslSocketFactory = sslContext.socketFactory
			val trustManagerFactory: TrustManagerFactory =
				TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
			trustManagerFactory.init(null as KeyStore?)
			val trustManagers: Array<TrustManager> =
				trustManagerFactory.trustManagers
			check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
				"Unexpected default trust managers:" + trustManagers.contentToString()
			}

			val trustManager =
				trustManagers[0] as X509TrustManager


			val builder = OkHttpClient.Builder()
			builder.sslSocketFactory(sslSocketFactory, trustManager)
			builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
			val logger = HttpLoggingInterceptor()
			logger.setLevel(HttpLoggingInterceptor.Level.BODY)
			builder.addInterceptor(logger)
			builder.build()
		} catch (e: Exception) {
			throw RuntimeException(e)
		}
	}
}