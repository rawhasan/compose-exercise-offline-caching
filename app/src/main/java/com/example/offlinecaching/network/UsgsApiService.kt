package com.example.offlinecaching.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// USGS API URL
// https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2021-01-01&endtime=2021-08-24&minmagnitude=4&latitude=24.0162182&longitude=90.6402874&maxradiuskm=400

private const val BASE_URL = "https://earthquake.usgs.gov/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface UsgsApiService {
    @GET("fdsnws/event/1/query")
    suspend fun getQuakes(
        @Query("format") format: String = "geojson",
        @Query("starttime") starttime: String = "2021-01-01",
        @Query("endtime") endtime: String = "2021-08-24",
        @Query("minmagnitude") minmagnitude: String = "4",
        @Query("latitude") latitude: String = "24.0162182",
        @Query("longitude") longitude: String = "90.6402874",
        @Query("maxradiuskm") maxradiuskm: String = "400"
    ): Quake
}

object UsgsApi {
    val retrofitService: UsgsApiService by lazy {
        retrofit.create(UsgsApiService::class.java)
    }
}