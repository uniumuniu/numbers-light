package com.example.numberslight.di

import com.example.numberslight.common.Constants
import com.example.numberslight.data.remote.NumbersApi
import com.example.numberslight.data.repository.NumbersRepository
import com.example.numberslight.domain.repository.INumbersRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideNumbersApi(): NumbersApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NumbersApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNumbersRepository(numbersApi: NumbersApi) : INumbersRepository {
        return NumbersRepository(numbersApi)
    }
}