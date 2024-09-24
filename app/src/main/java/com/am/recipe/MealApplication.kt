package com.am.recipe

import android.app.Application
import com.am.recipe.data.DefaultAppContainer

class MealApplication: Application() {

    lateinit var application: DefaultAppContainer

    override fun onCreate() {
        super.onCreate()
        application = DefaultAppContainer()
    }

}