package com.example.offlinecaching.network

import com.example.offlinecaching.database.DatabaseQuake
import com.example.offlinecaching.getDate
import com.example.offlinecaching.getPlace
import com.example.offlinecaching.getTime
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class NetworkQuake(
        @Json(name = "features")
        val features: List<Feature>
)

@JsonClass(generateAdapter = true)
data class Feature(
        @Json(name = "id")
        val id: String,
        @Json(name = "properties")
        val properties: Properties,
        @Json(name = "geometry")
        val geometry: Geometry
)

@JsonClass(generateAdapter = true)
data class Properties(
        @Json(name = "mag")
        val mag: Double,
        @Json(name = "place")
        val place: String,
        @Json(name = "time")
        val time: Long,
        @Json(name = "url")
        val url: String
)

@JsonClass(generateAdapter = true)
data class Geometry(
        @Json(name = "coordinates")
        val coordinates: List<Double>
)

fun NetworkQuake.asDatabaseModel(): List<DatabaseQuake> {
    return features.map {
        DatabaseQuake(
                id = it.id,
                mag = it.properties.mag,
                near = getPlace(it.properties.place)[0],
                place = getPlace(it.properties.place)[1],
                date = getDate(it.properties.time),
                time = getTime(it.properties.time),
                url = it.properties.url,
                lng = it.geometry.coordinates[0],
                lat = it.geometry.coordinates[1]
        )
    }
}

