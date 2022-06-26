package com.ekochkov.autodoctestapp.di.modules

import com.ekochkov.autodoctestapp.data.MainRepository
import com.ekochkov.autodoctestapp.utils.RetrofitInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideRepository(retrofit: RetrofitInterface) : MainRepository = MainRepository(retrofit)
}