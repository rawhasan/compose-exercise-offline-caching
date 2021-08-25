package com.example.offlinecaching.network

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class Quake(
    @Json(name = "features")
    val features: List<Feature>
)

@JsonClass(generateAdapter = true)
data class Feature(
    @Json(name = "id")
    val id: String?,
    @Json(name = "properties")
    val properties: Properties?,
    @Json(name = "geometry")
    val geometry: Geometry?
)

@JsonClass(generateAdapter = true)
data class Properties(
    @Json(name = "mag")
    val mag: Double?,
    @Json(name = "place")
    val place: String?,
    @Json(name = "time")
    val time: Long?,
    @Json(name = "url")
    val url: String?
)

@JsonClass(generateAdapter = true)
data class Geometry(
    @Json(name = "coordinates")
    val coordinates: List<Double>?
)