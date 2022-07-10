package id.io.android.olebsai.data.source.remote.user

import id.io.android.olebsai.data.model.request.user.login.LoginRequest
import id.io.android.olebsai.data.model.request.user.login.LoginWithOtpRequest
import id.io.android.olebsai.data.model.request.user.register.RegisterRequest
import id.io.android.olebsai.data.model.response.BaseResponse
import id.io.android.olebsai.data.model.response.user.UserResponse
import id.io.android.olebsai.data.model.response.user.login.LoginResponse
import id.io.android.olebsai.data.model.response.user.login.LoginWithOtpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    companion object {
        const val REGISTER = "v1/user/register/seller"
        const val LOGIN = "login"
        const val LOGIN_WITH_OTP = "login/otp"
    }

    @Headers("mock:true")
    @GET("/api/user/{id}")
    suspend fun getUser(@Path("id") id: Int): UserResponse

    @POST(REGISTER)
    suspend fun register(@Body request: RegisterRequest): BaseResponse<String>

    @POST(LOGIN)
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>

    @POST(LOGIN_WITH_OTP)
    suspend fun loginWithOtp(@Body request: LoginWithOtpRequest): BaseResponse<LoginWithOtpResponse>
}