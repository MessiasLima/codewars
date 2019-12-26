package io.github.messiaslima.codewars.repository.common.api

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module
class CodewarsServiceModule {

    @Provides
    fun providesCodewarsService(): CodewarsService {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.codewars.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create<CodewarsService>(
            CodewarsService::class.java
        )
    }
}