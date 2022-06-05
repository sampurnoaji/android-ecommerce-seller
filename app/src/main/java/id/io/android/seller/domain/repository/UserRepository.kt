package id.io.android.seller.domain.repository

import id.io.android.seller.domain.model.user.User
import id.io.android.seller.presentation.user.login.LoginParams
import id.io.android.seller.presentation.user.register.RegisterParams
import id.io.android.seller.util.LoadState

interface UserRepository {
    fun setLoggedIn(isLoggedIn: Boolean)
    fun isLoggedIn(): Boolean
    suspend fun getUser(id: Int): User
    suspend fun register(registerParams: RegisterParams): LoadState<String>
    suspend fun login(loginParams: LoginParams): LoadState<String>
}