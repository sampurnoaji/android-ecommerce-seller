package id.io.android.olebsai.domain.repository

import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.presentation.user.login.LoginParams
import id.io.android.olebsai.presentation.user.register.RegisterParams
import id.io.android.olebsai.util.LoadState

interface UserRepository {
    fun setLoggedIn(isLoggedIn: Boolean)
    fun isLoggedIn(): Boolean
    fun setToken(token: String)
    fun getToken(): String
    suspend fun getUser(id: Int): User
    suspend fun register(registerParams: RegisterParams): LoadState<String>
    suspend fun login(loginParams: LoginParams): LoadState<String>
    suspend fun loginWithOtp(loginParams: LoginParams): LoadState<String>
}