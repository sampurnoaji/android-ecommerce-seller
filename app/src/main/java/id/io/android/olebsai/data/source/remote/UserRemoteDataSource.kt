package id.io.android.olebsai.data.source.remote

import id.io.android.olebsai.data.model.request.user.login.LoginRequest
import id.io.android.olebsai.data.model.request.user.register.RegisterRequest
import id.io.android.olebsai.data.model.response.user.UserResponse
import id.io.android.olebsai.presentation.user.login.LoginParams
import id.io.android.olebsai.presentation.user.register.RegisterParams
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val api: ApiService) : ResponseHelper() {
    suspend fun getUser(id: Int): UserResponse = api.getUser(id)

    suspend fun register(params: RegisterParams): LoadState<String> {
        return call {
            api.register(
                RegisterRequest(
                    email = params.email,
                    namaUser = params.name,
                    nomorHp = params.phoneNumber,
                    namaToko = params.shopName,
                    password = params.password
                )
            ).message.orEmpty()
        }
    }

    suspend fun login(params: LoginParams): LoadState<String> {
        return call {
            api.login(
                LoginRequest(
                    email = params.email,
                    password = params.password
                )
            ).data?.token.orEmpty()
        }
    }
}