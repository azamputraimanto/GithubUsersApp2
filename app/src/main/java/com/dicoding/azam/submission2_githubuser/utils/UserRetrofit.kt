package com.dicoding.azam.submission2_githubuser.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRetrofit {
    private const val BASE_URL = "https://api.github.com/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInstance: Api = retrofit.create(Api::class.java)
}