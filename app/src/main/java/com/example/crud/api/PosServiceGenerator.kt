package com.example.crud.api

import com.example.crud.AppConstant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PosServiceGenerator {

    //base url
    private const val BASE_URL: String = "https://hsoprospex.com/api/"

    //instansiasi retrofit dan gson
    private var builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    //instansiasi dari okhttpclient
    private var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

    //instansiasi dari logging
    private var logging: HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    //instansiasi dari retrofit
    private var retrofit: Retrofit = builder.build()

    //fungsi yang berguna untuk membuat instans dari retrofit
    fun <S> createService(
        serviceClass: Class<S>
    ): S {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging)
            builder.client(httpClient.build())
            retrofit = builder.build()
        }
        return retrofit.create(serviceClass)
    }
}