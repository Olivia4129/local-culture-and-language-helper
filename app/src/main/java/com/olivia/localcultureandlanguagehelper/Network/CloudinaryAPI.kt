package com.olivia.localcultureandlanguagehelper.Network




import com.olivia.localcultureandlanguagehelper.models.CloudinaryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CloudinaryApi {
    @Multipart
    @POST("v1_1/dwnh5yt1t/image/upload") // 👈 replace dzbvmcs3d with your cloud name
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("upload_preset") uploadPreset: RequestBody
    ): Response<CloudinaryResponse>
}
