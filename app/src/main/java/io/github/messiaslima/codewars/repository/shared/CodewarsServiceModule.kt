package io.github.messiaslima.codewars.repository.shared

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class CodewarsServiceModule {

    @Provides
    fun providesCodewarsService(): CodewarsService {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.codewars.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create<CodewarsService>(CodewarsService::class.java)
    }
}