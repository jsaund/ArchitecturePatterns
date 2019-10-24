package com.architecture.patterns.repository

import com.architecture.patterns.App
import com.architecture.patterns.R
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object NewsApiRepository {
    private val API_KEY = App.context.getString(R.string.api_key)

    private const val HOST = "https://newsapi.org/v2/"
    private const val ENDPOINT_SOURCES = "sources?language=en&country=us"

    val api: NewsApiService

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .addInterceptor {
                val original = it.request()
                it.proceed(
                    original
                        .newBuilder()
                        .header("X-Api-Key", API_KEY)
                        .method(original.method(), original.body())
                        .build()
                )
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(NewsApiService::class.java)
    }

    interface NewsApiService {
        @GET(ENDPOINT_SOURCES)
        fun getSources(): Single<SourcesResponse>
    }
}
