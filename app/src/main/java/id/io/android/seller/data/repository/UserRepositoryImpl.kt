package id.io.android.seller.data.repository

import id.io.android.seller.data.source.local.UserLocalDataSource
import id.io.android.seller.data.source.remote.UserRemoteDataSource
import id.io.android.seller.domain.repository.UserRepository
import id.io.android.seller.domain.model.user.User
import id.io.android.seller.presentation.user.register.RegisterParams
import id.io.android.seller.util.LoadState
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun setLoggedIn(isLoggedIn: Boolean) {
        localDataSource.setLoggedIn(isLoggedIn)
    }

    override fun isLoggedIn(): Boolean = localDataSource.isLoggedIn()

    override suspend fun getUser(id: Int): User {
        return localDataSource.getUser()?.toDomain() ?: remoteDataSource.getUser(id).toDomain()
    }

    override suspend fun register(registerParams: RegisterParams): LoadState<String> {
        return remoteDataSource.register(registerParams)
    }
}