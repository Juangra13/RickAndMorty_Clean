package com.jgcb.rickandmorty.data.local.di

import com.jgcb.rickandmorty.data.local.DataBaseLocal
import com.jgcb.rickandmorty.data.local.dao.CharactersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun bindCharactersDao(db: DataBaseLocal): CharactersDao = db.charactersDao()
}