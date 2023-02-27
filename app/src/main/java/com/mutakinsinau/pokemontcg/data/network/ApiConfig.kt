package com.mutakinsinau.pokemontcg.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): PokemonService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor {
                    val response = it.proceed(
                        it.request().newBuilder()
                            .addHeader("X-Api-Key", "907bf52a-df4a-4cf1-9ea2-5566f2f7eda7").build()
                    )

                    response
                }
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.pokemontcg.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(PokemonService::class.java)
        }
    }
}