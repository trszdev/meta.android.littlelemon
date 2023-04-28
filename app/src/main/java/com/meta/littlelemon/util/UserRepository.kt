package com.meta.littlelemon.util

import android.content.SharedPreferences
import androidx.compose.runtime.compositionLocalOf
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UserRepository {
    val user: LiveData<User?>
    suspend fun save(user: User?)
}

val LocalUserRepository = compositionLocalOf<UserRepository> { UserRepositoryStub() }

private class UserRepositoryStub: UserRepository {
    override val user: LiveData<User?>
        get() = liveData {}

    override suspend fun save(user: User?) {
    }
}

class UserSharedPreferencesRepository(
    private val sharedPreferences: SharedPreferences
): UserRepository {
    private val userLiveData = MutableLiveData<User?>(null)

    override val user: LiveData<User?>
        get() = userLiveData.map { it }

    fun loadUser(): Boolean {
        val firstName = sharedPreferences.getString(UserKey.FirstName.name, "")
        if (firstName.isNullOrBlank()) return false
        val lastName = sharedPreferences.getString(UserKey.LastName.name, "")
        if (lastName.isNullOrBlank()) return false
        val email = sharedPreferences.getString(UserKey.Email.name, "")
        if (email.isNullOrBlank()) return false
        val user = User(firstName, lastName, email)
        userLiveData.value = user
        return true
    }

    override suspend fun save(user: User?) {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit(commit = true) {
                if (user == null) {
                    remove(UserKey.FirstName.name)
                    remove(UserKey.LastName.name)
                    remove(UserKey.Email.name)
                } else {
                    putString(UserKey.FirstName.name, user.firstName)
                    putString(UserKey.LastName.name, user.lastName)
                    putString(UserKey.Email.name, user.email)
                }
            }
        }
        userLiveData.value = user
    }
}

private enum class UserKey {
    FirstName,
    LastName,
    Email
 }