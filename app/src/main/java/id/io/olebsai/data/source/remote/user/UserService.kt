package id.io.olebsai.data.source.remote.user

import id.io.olebsai.data.model.request.user.login.LoginRequest
import id.io.olebsai.data.model.request.user.login.LoginWithOtpRequest
import id.io.olebsai.data.model.request.user.register.RegisterRequest
import id.io.olebsai.data.model.response.BaseResponse
import id.io.olebsai.data.model.response.user.DistrictResponse
import id.io.olebsai.data.model.response.user.ProvinceResponse
import id.io.olebsai.data.model.response.user.SubDistrictResponse
import id.io.olebsai.data.model.response.user.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    companion object {
        const val REGISTER = "v1/user/register/seller"
        const val LOGIN = "login"
        const val LOGIN_WITH_OTP = "login/otp"
    }

    @POST(REGISTER)
    suspend fun register(@Body request: RegisterRequest): BaseResponse<String>

    @POST(LOGIN)
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>

    @POST(LOGIN_WITH_OTP)
    suspend fun loginWithOtp(@Body request: LoginWithOtpRequest): BaseResponse<String>

    @GET("v1/raja-ongkir/get-propinsi")
    suspend fun getProvinces(): BaseResponse<List<ProvinceResponse>>

    @GET("v1/raja-ongkir/get-kota/{idPropinsi}")
    suspend fun getDistricts(@Path("idPropinsi") idPropinsi: String): BaseResponse<List<DistrictResponse>>

    @GET("v1/raja-ongkir/get-kecamatan/{idKota}")
    suspend fun getSubDistricts(@Path("idKota") idKota: String): BaseResponse<List<SubDistrictResponse>>
}