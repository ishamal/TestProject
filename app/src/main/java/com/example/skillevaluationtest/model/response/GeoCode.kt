package com.example.skillevaluationtest.model.response

import java.io.Serializable

data class GeoCode(
    val lat: String?,
    val lng: String
) : Serializable