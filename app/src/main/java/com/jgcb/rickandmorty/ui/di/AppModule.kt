package com.jgcb.rickandmorty.ui.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jgcb.rickandmorty.data.local.CharactersDao
import com.jgcb.rickandmorty.data.local.DataBaseLocal
import com.jgcb.rickandmorty.data.remote.CharacterNetworkDataSource
import com.jgcb.rickandmorty.data.repository.CharactersRepositoryImpl
import com.jgcb.rickandmorty.domain.repository.CharactersRepository
import com.jgcb.rickandmorty.domain.services.CharactersService
import com.jgcb.rickandmorty.utils.ContentConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(ContentConstants.BASE_URL_PATH)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCharactersService(retrofit: Retrofit): CharactersService =
        retrofit.create(CharactersService::class.java)

    @Singleton
    @Provides
    fun provideCharacterNetworkDataSource(charactersService: CharactersService) =
        CharacterNetworkDataSource(charactersService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        DataBaseLocal.getDataBaseLocal(appContext)

    @Singleton
    @Provides
    fun provideCharactersDao(db: DataBaseLocal) = db.charactersDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: CharacterNetworkDataSource,
            localDataSource: CharactersDao) : CharactersRepository =
        CharactersRepositoryImpl(remoteDataSource, localDataSource)
}