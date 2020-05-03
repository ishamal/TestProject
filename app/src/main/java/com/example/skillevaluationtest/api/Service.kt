@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.skillevaluationtest.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Service{

    val BASE_URL : String? = "https://jsonplaceholder.typicode.com"

    fun createService() : ApiService {

        val retrofit = Retrofit.Builder().addCallAdapterFactory(
            RxJava2CallAdapterFactory.create()).addConverterFactory(
            GsonConverterFactory.create()
        ).baseUrl(BASE_URL).build()

        return retrofit.create(ApiService::class.java)
    }

}