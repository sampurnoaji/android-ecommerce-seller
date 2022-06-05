package id.io.android.seller.data.source.remote

import id.io.android.seller.data.model.request.user.register.RegisterRequest
import id.io.android.seller.data.model.response.user.UserResponse
import id.io.android.seller.presentation.user.register.RegisterParams
import id.io.android.seller.util.LoadState
import id.io.android.seller.util.remote.ResponseHelper
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
                    password = params.password
                )
            ).message.orEmpty()
        }
    }
}