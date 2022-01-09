package com.example.numberslight

import android.app.Application
import com.example.numberslight.di.AppComponent
import com.example.numberslight.di.DaggerAppComponent

class NumbersApplication : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}