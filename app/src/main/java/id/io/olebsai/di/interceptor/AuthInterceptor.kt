package id.io.olebsai.di.interceptor

import android.content.SharedPreferences
import id.io.olebsai.data.source.remote.product.ProductService
import id.io.olebsai.data.source.remote.user.UserService
import id.io.olebsai.di.SharedPrefModule
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor @Inject constructor(private val preferences: SharedPreferences) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.toString()
        val isUploadImage = url.contains(ProductService.IMAGE_UPLOAD_URL)
        val requestBuilder = chain.request().newBuilder().apply {
            if (isUploadImage) addImageUploadBearerAuthorization()
            else addBearerAuthorization()
        }

        if (url.isNoNeedBearerAuthorization()) {
            requestBuilder.removeBearerAuthorization()
        }

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }

    private fun String.isNoNeedBearerAuthorization(): Boolean {
        val urls = listOf(
            UserService.REGISTER,
            UserService.LOGIN,
            UserService.LOGIN_WITH_OTP,
        )
        for (url in urls) {
            if (contains(url)) return true
        }
        return false
    }

    private fun Request.Builder.addBearerAuthorization(): Request.Builder {
        val token = preferences.getString(SharedPrefModule.KEY_USER_TOKEN, null)
        return addHeader("token", "Bearer $token")
    }

    private fun Request.Builder.addImageUploadBearerAuthorization(): Request.Builder {
        return addHeader(
            "Authorization",
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijk0NGY5MzhiNmFiOGVmZTU0OWZhZjMyNmEwYzM4OTFhYWMyNWUyZDBjODJhZTRkZDhmNTJiYWFmYzY0MzQ5YTYyZDMyM2NjN2RlYjFkYjA2In0.eyJhdWQiOiI5IiwianRpIjoiOTQ0ZjkzOGI2YWI4ZWZlNTQ5ZmFmMzI2YTBjMzg5MWFhYzI1ZTJkMGM4MmFlNGRkOGY1MmJhYWZjNjQzNDlhNjJkMzIzY2M3ZGViMWRiMDYiLCJpYXQiOjE2OTI3MDk5ODcsIm5iZiI6MTY5MjcwOTk4NywiZXhwIjoxNzI0MzMyMzg3LCJzdWIiOiIxNTkiLCJzY29wZXMiOltdfQ.qQR6XETfeDc8T5D0aOeePlVlw5Ul02aq_FJUOLy-0NAv1DR24Ps_nPLMmmCsZXCJH67Sd9jJ9socMyPihCfkj1-nc2TqjJn_59SmnSH_UmifHS_tcU_MiZG0sCgiSAu6KAa0PB03Mz_Yt6tzx1Vwoy8rOS5WetLqqMhaHKeqeaIJN8k9ac2lfSvBniblBgyNYBVa0i413QBu3FCiePbLx4lgAAHmgnnSgSCuJG8c6iDZHfg-jQ15ABpOQVh44Zt5xD_v5McNqW47Nprnn-6Jo8gF0g6PKvhDFAAz7Ux-siF0APQhJuFs9QMYK7sJS52DzvUQu5pBozB2fB6SqDpvaf6_bDJzdkoeQDqUq2z-qL5-cRHF1S3Jj2bnDgChGQv98iQFyU7GYGTfx83ETabIgdN0jHhTuyrgAG8VAKc5NH_5qDvwq0O8LZdTNDp0KX0RHPl3hyc8yDgXCfQmnxBsqjDEg7A-akiKgeiv-cuqfZJ-qKGD3Mr_jZH0P4NcQpJ7fbjiOBES3AHKS9zWBNInZ8HxF8Uw7kO1oTc9GQxvrV0PBqMGFf81DxgpSxRaeTVscChs-pXHgo-OGZAXGW1wxGLw2FzmIpTfwdsFmPk3Npv0s_UcvtaMwxxHH0WyCd0OiyjPGBeUio6oLU2cVx4_d9VW7ZZEBzFYvSlIHII-mao"
        )
    }

    private fun Request.Builder.removeBearerAuthorization(): Request.Builder {
        return removeHeader("token")
    }
}