package com.example.offlinecaching.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

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

// Expected date format: 2021-09-01 (Tested - OK)
interface UsgsApiService {
    @GET("fdsnws/event/1/query")
    suspend fun getQuakes(
        @Query("format") format: String = "geojson",
        @Query("starttime") starttime: String = LocalDate.now().minusYears(1)
            .toString(), // subtract 1 year from today
        @Query("minmagnitude") minmagnitude: String = "4",
        @Query("latitude") latitude: String = "24.0162182",
        @Query("longitude") longitude: String = "90.6402874",
        @Query("maxradiuskm") maxradiuskm: String = "400",
        @Query("orderby") orderby: String = "time",
    ): NetworkQuake

    @GET("fdsnws/event/1/query")
    suspend fun getLatestQuake(
        @Query("format") format: String = "geojson",
        @Query("starttime") starttime: String = LocalDate.now().minusYears(1)
            .toString(), // subtract 1 year from today
        @Query("minmagnitude") minmagnitude: String = "4",
        @Query("latitude") latitude: String = "24.0162182",
        @Query("longitude") longitude: String = "90.6402874",
        @Query("maxradiuskm") maxradiuskm: String = "400",
        @Query("orderby") orderby: String = "time",
        @Query("limit") limit: String = "1"
    ): NetworkQuake
}

object UsgsApi {
    val retrofitService: UsgsApiService by lazy {
        retrofit.create(UsgsApiService::class.java)
    }
}