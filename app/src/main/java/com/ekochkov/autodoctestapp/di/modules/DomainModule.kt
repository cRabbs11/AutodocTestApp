package com.ekochkov.autodoctestapp.di.modules

import com.ekochkov.autodoctestapp.data.MainRepository
import com.ekochkov.autodoctestapp.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository): Interactor = Interactor(repository)
}