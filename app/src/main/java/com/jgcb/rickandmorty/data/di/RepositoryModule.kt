package com.jgcb.rickandmorty.data.di

import com.jgcb.rickandmorty.data.repository.CharactersRepositoryImpl
import com.jgcb.rickandmorty.domain.repository.CharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCharactersRepository(impl: CharactersRepositoryImpl): CharactersRepository
}