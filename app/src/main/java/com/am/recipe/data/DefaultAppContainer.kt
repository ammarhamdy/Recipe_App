package com.am.recipe.data

import com.am.recipe.data.remote.MealApi
import com.am.recipe.data.repository.MealRepositoryImp
import com.am.recipe.domain.model.AppContainer
import com.am.recipe.domain.repository.MealRepository
import com.am.recipe.util.Constants.BAS_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class DefaultAppContainer : AppContainer {

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BAS_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val retrofitService: MealApi by lazy {
        retrofit.create(MealApi::class.java)
    }


    override val mealsRepository: MealRepository by lazy {
        MealRepositoryImp(retrofitService)
    }

}