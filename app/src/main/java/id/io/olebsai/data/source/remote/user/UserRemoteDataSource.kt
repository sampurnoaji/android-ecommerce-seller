package id.io.olebsai.data.source.remote.user

import id.io.olebsai.data.model.request.user.login.LoginRequest
import id.io.olebsai.data.model.request.user.login.LoginWithOtpRequest
import id.io.olebsai.data.model.request.user.register.RegisterRequest
import id.io.olebsai.data.model.response.user.DistrictResponse
import id.io.olebsai.data.model.response.user.ProvinceResponse
import id.io.olebsai.data.model.response.user.SubDistrictResponse
import id.io.olebsai.data.model.response.user.login.LoginResponse
import id.io.olebsai.presentation.user.login.LoginParams
import id.io.olebsai.presentation.user.register.RegisterParams
import id.io.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val api: UserService) : ResponseHelper() {

    suspend fun register(params: RegisterParams): String {
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

    suspend fun login(params: LoginParams): LoginResponse {
        return call {
            api.login(LoginRequest(email = params.email, password = params.password!!)).data
        }
    }

    suspend fun loginWithOtp(params: LoginParams): String {
        return call {
            api.loginWithOtp(LoginWithOtpRequest(email = params.email, otp = params.otp!!)).data
        }
    }

    suspend fun getProvinces(): List<ProvinceResponse> {
        return call { api.getProvinces().data }
    }

    suspend fun getDistricts(idPropinsi: String): List<DistrictResponse> {
        return call { api.getDistricts(idPropinsi).data }
    }

    suspend fun getSubDistricts(idKota: String): List<SubDistrictResponse> {
        return call { api.getSubDistricts(idKota).data }
    }
}