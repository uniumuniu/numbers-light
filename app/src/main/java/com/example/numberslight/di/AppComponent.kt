package com.example.numberslight.di

import com.example.numberslight.presentation.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun viewModelFactory(): ViewModelFactory
}