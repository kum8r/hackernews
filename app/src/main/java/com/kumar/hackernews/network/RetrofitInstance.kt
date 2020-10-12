package com.kumar.hackernews.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {


    companion object {
        private val BASE_URL = "https://hacker-news.firebaseio.com/v0/"

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api:APIService by lazy {
            retrofit.create(APIService::class.java)
        }
    }
}