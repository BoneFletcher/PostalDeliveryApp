package com.sdv.postaldeliveryapp.di

import com.sdv.net.PostAPI
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


const val BASE_URL = "https://jsonplaceholder.typicode.com/"
val network: Module = module {
    single { provideHttpClient() }
    single { provideMoshi() }
    single { provideRetrofit(get(), get()) }
    single { provideService(get()) }
}


fun provideHttpClient(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()
    return okHttpClientBuilder.build()
}

fun provideMoshi(): Moshi {
    return Moshi.Builder().build()
}

fun provideRetrofit(factory: Moshi, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(factory))
        .client(client)
        .build()
}

fun provideService(retrofit: Retrofit): PostAPI {
    return retrofit.create(PostAPI::class.java)
}