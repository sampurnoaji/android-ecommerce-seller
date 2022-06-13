package id.io.android.olebsai.data.source.local

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import id.io.android.olebsai.di.SharedPrefModule
import id.io.android.olebsai.domain.model.user.User
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val pref: SharedPreferences,
    private val prefEditor: SharedPreferences.Editor
) {
    fun isFirstLaunchApp(): Boolean =
        pref.getBoolean(SharedPrefModule.KEY_IS_FIRST_LAUNCH_APP, true)

    fun setFirstLaunchApp(isFirstLaunchApp: Boolean) {
        prefEditor.putBoolean(SharedPrefModule.KEY_IS_FIRST_LAUNCH_APP, isFirstLaunchApp)
            .apply()
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        prefEditor.putBoolean(SharedPrefModule.KEY_IS_LOGGED_IN, isLoggedIn)
            .apply()
    }

    fun isLoggedIn(): Boolean = pref.getBoolean(SharedPrefModule.KEY_IS_LOGGED_IN, false)

    fun setToken(token: String) {
        prefEditor.putString(SharedPrefModule.KEY_USER_TOKEN, token)
            .apply()
    }

    fun getToken(): String = pref.getString(SharedPrefModule.KEY_USER_TOKEN, "").orEmpty()

    fun saveUser(user: User) {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(User::class.java)
        prefEditor.putString(SharedPrefModule.KEY_USER, adapter.toJson(user))
            .apply()
    }

    fun getUser(): User? {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(User::class.java)
        val json = pref.getString(SharedPrefModule.KEY_USER, null)
        if (json != null) return adapter.fromJson(json)
        return null
    }
}