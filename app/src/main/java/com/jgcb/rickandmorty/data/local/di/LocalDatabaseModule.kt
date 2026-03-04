package com.jgcb.rickandmorty.data.local.di

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