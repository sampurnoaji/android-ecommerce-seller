package id.io.android.olebsai.data.repository

import id.io.android.olebsai.data.source.local.UserLocalDataSource
import id.io.android.olebsai.data.source.remote.user.UserRemoteDataSource
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.presentation.user.login.LoginParams
import id.io.android.olebsai.presentation.user.register.RegisterParams
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : ResponseHelper(), UserRepository {

    override fun setLoggedIn(isLoggedIn: Boolean) {
        localDataSource.setLoggedIn(isLoggedIn)
    }

    override fun isLoggedIn(): Boolean = localDataSource.isLoggedIn()
    override fun setToken(token: String) {
        localDataSource.setToken(token)
    }

    override fun getToken(): String = localDataSource.getToken()

    override suspend fun getUser(id: Int): User {
        return localDataSource.getUser()?.toDomain() ?: remoteDataSource.getUser(id).toDomain()
    }

    override suspend fun register(registerParams: RegisterParams): LoadState<String> {
        return map { remoteDataSource.register(registerParams) }
    }

    override suspend fun login(loginParams: LoginParams): LoadState<String> {
        return map { remoteDataSource.login(loginParams).token.orEmpty() }
    }

    override suspend fun loginWithOtp(loginParams: LoginParams): LoadState<String> {
        return map { remoteDataSource.loginWithOtp(loginParams).token.orEmpty() }
    }
}