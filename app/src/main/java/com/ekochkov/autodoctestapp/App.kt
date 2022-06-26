package com.ekochkov.autodoctestapp

import android.app.Application
import com.ekochkov.autodoctestapp.di.AppComponent
import com.ekochkov.autodoctestapp.di.DaggerAppComponent
import com.ekochkov.autodoctestapp.di.modules.DataModule
import com.ekochkov.autodoctestapp.di.modules.DomainModule
import com.ekochkov.autodoctestapp.di.modules.RemoteModule

class App: Application() {

    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        dagger = DaggerAppComponent.builder()
            .remoteModule(RemoteModule())
            .domainModule(DomainModule())
            .dataModule(DataModule())
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}