package id.io.olebsai.domain.repository

import id.io.olebsai.domain.model.user.Address
import id.io.olebsai.domain.model.user.User
import id.io.olebsai.presentation.user.login.LoginParams
import id.io.olebsai.presentation.user.register.RegisterParams
import id.io.olebsai.util.LoadState

interface UserRepository {
    fun isFirstLaunchApp(): Boolean
    fun setFirstLaunchApp(isFirstLaunchApp: Boolean)
    fun setLoggedIn(isLoggedIn: Boolean)
    fun isLoggedIn(): Boolean
    fun setToken(token: String)
    fun getToken(): String
    fun saveUser(user: User)
    fun getUser(): User?

    suspend fun register(registerParams: RegisterParams): LoadState<String>
    suspend fun login(loginParams: LoginParams): LoadState<Pair<User, String>>
    suspend fun loginWithOtp(loginParams: LoginParams): LoadState<String>
    suspend fun getProvinces(): LoadState<List<Address>>
    suspend fun getDistricts(idPropinsi: String): LoadState<List<Address>>
    suspend fun getSubDistricts(idKota: String): LoadState<List<Address>>
}