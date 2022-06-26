package com.ekochkov.autodoctestapp.di

import com.ekochkov.autodoctestapp.data.entity.UserDTO
import com.ekochkov.autodoctestapp.di.modules.DataModule
import com.ekochkov.autodoctestapp.di.modules.DomainModule
import com.ekochkov.autodoctestapp.di.modules.RemoteModule
import com.ekochkov.autodoctestapp.viewModel.SearchRepoViewModel
import com.ekochkov.autodoctestapp.viewModel.UserViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    RemoteModule::class,
    DomainModule::class,
    DataModule::class])
interface AppComponent {

    fun inject(searchRepoViewModel: SearchRepoViewModel)
    fun inject(userViewModel: UserViewModel)
}