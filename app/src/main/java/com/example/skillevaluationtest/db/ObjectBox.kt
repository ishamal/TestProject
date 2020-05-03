package com.example.skillevaluationtest.db

import android.content.Context
import android.util.Log
import com.example.skillevaluationtest.BuildConfig
import com.example.skillevaluationtest.application.App
import com.example.skillevaluationtest.model.db.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser

object ObjectBox {

    lateinit var boxStore: BoxStore
        private set

        fun init(context: Context) {

        boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()
        if (BuildConfig.DEBUG) {
            Log.d(App.TAG, "Using ObjectBox ${BoxStore.getVersion()} (${BoxStore.getVersionNative()})")
            AndroidObjectBrowser(boxStore).start(context.applicationContext)
        }

    }

}