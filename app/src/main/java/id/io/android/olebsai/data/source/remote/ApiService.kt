package id.io.android.olebsai.data.source.remote

import id.io.android.olebsai.data.model.request.user.login.LoginRequest
import id.io.android.olebsai.data.model.request.user.register.RegisterRequest
import id.io.android.olebsai.data.model.response.BaseResponse
import id.io.android.olebsai.data.model.response.user.UserResponse
import id.io.android.olebsai.data.model.response.user.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @Headers("mock:true")
    @GET("/api/user/{id}")
    suspend fun getUser(@Path("id") id: Int): UserResponse

    @POST("v1/user/register/seller")
    suspend fun register(@Body request: RegisterRequest): BaseResponse

    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}