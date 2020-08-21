package com.sdv.domain.model.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Geo(
    @Json(name = "lat")
    val lat: String,
    @Json(name = "lng")
    val lng: String
)