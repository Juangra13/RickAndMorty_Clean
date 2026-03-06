package com.jgcb.rickandmorty.data.network.di

import com.jgcb.rickandmorty.data.network.repository.NetworkDataSource
import com.jgcb.rickandmorty.data.network.repository.NetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDataSourceModule {

    @Binds
    abstract fun bindNetworkDataSource(impl: NetworkDataSourceImpl): NetworkDataSource
}