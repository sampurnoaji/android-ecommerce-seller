package id.io.olebsai.data.repository

import id.io.olebsai.data.source.local.UserLocalDataSource
import id.io.olebsai.data.source.remote.user.UserRemoteDataSource
import id.io.olebsai.domain.model.user.Address
import id.io.olebsai.domain.model.user.User
import id.io.olebsai.domain.repository.UserRepository
import id.io.olebsai.presentation.user.login.LoginParams
import id.io.olebsai.presentation.user.register.RegisterParams
import id.io.olebsai.util.LoadState
import id.io.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : ResponseHelper(), UserRepository {

    override fun isFirstLaunchApp(): Boolean = localDataSource.isFirstLaunchApp()
    override fun setFirstLaunchApp(isFirstLaunchApp: Boolean) {
        localDataSource.setFirstLaunchApp(isFirstLaunchApp)
    }

    override fun setLoggedIn(isLoggedIn: Boolean) {
        localDataSource.setLoggedIn(isLoggedIn)
    }

    override fun isLoggedIn(): Boolean = localDataSource.isLoggedIn()
    override fun setToken(token: String) {
        localDataSource.setToken(token)
    }

    override fun getToken(): String = localDataSource.getToken()

    override fun saveUser(user: User) {
        localDataSource.saveUser(user)
    }

    override fun getUser(): User? = localDataSource.getUser()

    override suspend fun register(registerParams: RegisterParams): LoadState<String> {
        return map { remoteDataSource.register(registerParams) }
    }

    override suspend fun login(loginParams: LoginParams): LoadState<Pair<User, String>> {
        return map {
            val result = remoteDataSource.login(loginParams)
            Pair(result.toDomain(), result.token.orEmpty())
        }
    }

    override suspend fun loginWithOtp(loginParams: LoginParams): LoadState<String> {
        return map { remoteDataSource.loginWithOtp(loginParams) }
    }

    override suspend fun getProvinces(): LoadState<List<Address>> {
        return map {
            remoteDataSource.getProvinces().map {
                Address(id = it.idPropinsi.orEmpty(), name = it.namaPropinsi.orEmpty())
            }
        }
    }

    override suspend fun getDistricts(idPropinsi: String): LoadState<List<Address>> {
        return map {
            remoteDataSource.getDistricts(idPropinsi).map {
                Address(id = it.idKota.orEmpty(), name = it.namaKota.orEmpty())
            }
        }
    }

    override suspend fun getSubDistricts(idKota: String): LoadState<List<Address>> {
        return map {
            remoteDataSource.getSubDistricts(idKota).map {
                Address(id = it.idKecamatan.orEmpty(), name = it.namaKecamatan.orEmpty())
            }
        }
    }
}