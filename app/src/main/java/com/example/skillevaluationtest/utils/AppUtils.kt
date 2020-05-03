@file:Suppress("DEPRECATION")

package com.example.skillevaluationtest.utils

import android.content.Context
import android.net.ConnectivityManager

object AppUtils {

    fun hasNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        return (network != null)
    }
}