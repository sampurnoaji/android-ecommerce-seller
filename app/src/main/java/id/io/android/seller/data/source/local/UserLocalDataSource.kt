package id.io.android.seller.data.source.local

import android.content.SharedPreferences
import id.io.android.seller.data.model.entity.UserEntity
import id.io.android.seller.di.SharedPrefModule
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val userDao: UserDao,
    private val pref: SharedPreferences,
    private val prefEditor: SharedPreferences.Editor
) {
    suspend fun getUser(): UserEntity? = userDao.getUser()

    fun setLoggedIn(isLoggedIn: Boolean) {
        prefEditor.putBoolean(SharedPrefModule.KEY_IS_LOGGED_IN, isLoggedIn)
            .apply()
    }

    fun isLoggedIn(): Boolean = pref.getBoolean(SharedPrefModule.KEY_IS_LOGGED_IN, false)
}