package com.example.skillevaluationtest.application

import android.app.Application
import com.example.skillevaluationtest.db.ObjectBox

class App : Application() {

    companion object Constants {

        const val TAG = "ObjectBoxInit"

    }


    override fun onCreate() {

        super.onCreate()
        ObjectBox.init(this)

    }

}