package com.ekochkov.autodoctestapp.di.modules

import com.ekochkov.autodoctestapp.utils.ApiConstants
import com.ekochkov.autodoctestapp.utils.RetrofitInterface
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RemoteModule {

    private val CALL_TIMEOUT_MILLI_30 = 30L

    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            callTimeout(CALL_TIMEOUT_MILLI_30, TimeUnit.SECONDS)
            readTimeout(CALL_TIMEOUT_MILLI_30, TimeUnit.SECONDS)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }).build()
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): RetrofitInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build().create(RetrofitInterface::class.java)
        return retrofit
    }
}