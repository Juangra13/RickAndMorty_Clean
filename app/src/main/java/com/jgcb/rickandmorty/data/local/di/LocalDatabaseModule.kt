package com.jgcb.rickandmorty.data.local.di

import com.jgcb.rickandmorty.data.local.repository.DatabaseLocalResource
import com.jgcb.rickandmorty.data.local.repository.DatabaseLocalResourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDatabaseModule {

    @Binds
    abstract fun provideLocalDatabase(impl: DatabaseLocalResourceImpl): DatabaseLocalResource
}