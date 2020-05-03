package com.example.skillevaluationtest.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Address (
    val street : String?,
    val city : String?,
    @SerializedName("zipcode")
    val zipCode : String?,
    val geo : GeoCode
) : Serializable