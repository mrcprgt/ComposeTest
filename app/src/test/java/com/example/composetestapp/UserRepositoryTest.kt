package com.example.composetestapp.data

import Coordinates
import Dob
import Id
import Info
import Location
import Login
import Name
import Picture
import Registered
import RemoteUser
import RemoteUserResponse
import Street
import Timezone
import android.database.sqlite.SQLiteConstraintException
import com.example.composetestapp.data.local.LocalUser
import com.example.composetestapp.data.local.UserDao
import com.example.composetestapp.data.remote.RandomUserService
import com.example.composetestapp.domain.User
import com.example.composetestapp.util.CustomException
import com.example.composetestapp.util.Resource
import com.example.composetestapp.util.toLocalUser
import com.example.composetestapp.util.toUser
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

	@Mock
	private lateinit var userDao: UserDao

	@Mock
	private lateinit var userService: RandomUserService

	private lateinit var userRepository: UserRepositoryImpl

	@Before
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		userRepository = UserRepositoryImpl(userDao, userService)
	}

	@Test
	fun `getUsers should return success resource`() = runTest {
		val localUsers = listOf(
			LocalUser(
				uuid = "1",
				gender = "Male",
				name = "John Doe",
				location = "New York",
				email = "john.doe@example.com",
				birthday = "1990-01-01",
				phone = "123-456-7890",
				cell = "098-765-4321",
				picture = com.example.composetestapp.data.local.Picture(
					large = "large1.jpg",
					thumbnail = "thumb1.jpg"
				),
				nationality = "American",
				age = 35,
				isFavorite = false
			)
		)
		val users = localUsers.map { it.toUser() }

		`when`(userDao.getUsers()).thenReturn(localUsers)

		val result = userRepository.getUsers()

		assertEquals(Resource.Success(users).data, result.data)
	}

	@Test
	fun `getUsers should return error resource`() = runTest {
		val errorMessage = "Database error"

		`when`(userDao.getUsers()).thenThrow(RuntimeException(errorMessage))

		val result = userRepository.getUsers()

		assert(result is Resource.Error)
	}

	@Test
	fun `saveUser should save user successfully`() = runTest {
		val user = User(
			id = "1",
			name = "John Doe",
			gender = "Male",
			location = "New York",
			birthday = "1992-03-08T15:13:16.688Z",
			age = 35,
			phone = "123-456-7890",
			cell = "098-765-4321",
			picture = com.example.composetestapp.domain.Picture(
				thumb = "thumb1.jpg",
				large = "large1.jpg"
			),
			nationality = "American",
			email = "john.doe@example.com",
			isFavorite = false
		)

		userRepository.saveUser(user)

		verify(userDao).saveUser(user.toLocalUser())
	}

	@Test
	fun `saveUser should throw CustomException when user already exists`() = runTest {
		val user = User(
			id = "1",
			name = "John Doe",
			gender = "Male",
			location = "New York",
			birthday = "1990-01-01",
			age = 35,
			phone = "123-456-7890",
			cell = "098-765-4321",
			picture = com.example.composetestapp.domain.Picture(
				thumb = "thumb1.jpg",
				large = "large1.jpg"
			),
			nationality = "American",
			email = "john.doe@example.com",
			isFavorite = false
		)

		`when`(userDao.saveUser(user.toLocalUser())).thenThrow(SQLiteConstraintException())

		val exception = assertFailsWith<CustomException> {
			userRepository.saveUser(user)
		}

		assertEquals("User is already in favorites.", exception.message)
	}

	@Test
	fun `deleteUser should delete user successfully`() = runTest {
		val user = User(
			id = "1",
			name = "John Doe",
			gender = "Male",
			location = "New York",
			birthday = "1990-01-01",
			age = 35,
			phone = "123-456-7890",
			cell = "098-765-4321",
			picture = com.example.composetestapp.domain.Picture(
				thumb = "thumb1.jpg",
				large = "large1.jpg"
			),
			nationality = "American",
			email = "john.doe@example.com",
			isFavorite = false
		)

		userRepository.deleteUser(user)

		verify(userDao).deleteUser(user.toLocalUser())
	}

	@Test
	fun `fetchUsers should return success resource`() = runTest {
		val remoteUsers = listOf(
			RemoteUser(
				gender = "Male",
				name = Name(
					title = "Mr",
					first = "John",
					last = "Doe"
				),
				location = Location(
					street = Street(
						number = 123,
						name = "Main St"
					),
					city = "New York",
					state = "NY",
					country = "USA",
					postcode = "10001",
					coordinates = Coordinates(
						latitude = "40.7128",
						longitude = "-74.0060"
					),
					timezone = Timezone(
						offset = "-5:00",
						description = "Eastern Time (US & Canada)"
					)
				),
				email = "john.doe@example.com",
				login = Login(
					uuid = "1",
					username = "johndoe",
					password = "password",
					salt = "salt",
					md5 = "md5",
					sha1 = "sha1",
					sha256 = "sha256"
				),
				dob = Dob(
					date = "1992-03-08T15:13:16.688Z",
					age = 35
				),
				registered = Registered(
					date = "1992-03-08T15:13:16.688Z",
					age = 10
				),
				phone = "123-456-7890",
				cell = "098-765-4321",
				id = Id(
					name = "SSN",
					value = "123-45-6789"
				),
				picture = Picture(
					large = "large1.jpg",
					medium = "medium1.jpg",
					thumbnail = "thumb1.jpg"
				),
				nat = "US"
			)
		)
		val users = remoteUsers.map { it.toUser() }

		`when`(userService.getUsers(1)).thenReturn(
			RemoteUserResponse(
				results = remoteUsers, info = Info(
					seed = "seed",
					results = 1,
					page = 1,
					version = "1.0"
				)
			)
		)

		val result = userRepository.fetchUsers(1)

		assertEquals(Resource.Success(users).data, result.data)
	}

	@Test
	fun `fetchUsers should return error resource`() = runTest {
		val errorMessage = "Network error"

		`when`(userService.getUsers(1)).thenThrow(RuntimeException(errorMessage))

		val result = userRepository.fetchUsers(1)

		assert(result is Resource.Error)
	}
}
