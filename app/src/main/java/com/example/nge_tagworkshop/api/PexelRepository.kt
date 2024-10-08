package com.example.nge_tagworkshop.api

import com.example.nge_tagworkshop.BuildConfig
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    @SerializedName("photographer_url") val photographerUrl: String,
    @SerializedName("photographer_id") val photographerId: Int,
    @SerializedName("avg_color") val avgColor: String?,
    val src: Src,
    val liked: Boolean
)

data class Src(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)

class PexelRepository {
    private var service: PexelService

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", BuildConfig.PEXELS_API_KEY)
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .client(client) // Add the custom OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(PexelService::class.java)
    }

    suspend fun getPhotos(query: String, count: Int): List<Photo> {
        val response = service.searchForImage(query = query, perPage = count)
        return response.photos.take(count)
    }

    suspend fun getPhoto(query: String): Photo? {
        val response = service.searchForImage(query = query, perPage = 1)
        return response.photos.firstOrNull()
    }
}

interface PexelService {

    @GET("search")
    suspend fun searchForImage(
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ): PexelResponse
}

// Data class to map the API response
data class PexelResponse(
    val photos: List<Photo>
)