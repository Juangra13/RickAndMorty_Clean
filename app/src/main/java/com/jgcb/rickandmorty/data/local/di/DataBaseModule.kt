package com.jgcb.rickandmorty.data.local.di

import android.content.Context
import androidx.room.Room
import com.jgcb.rickandmorty.data.local.DataBaseLocal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBaseLocal(
        @ApplicationContext context: Context
    ): DataBaseLocal = Room.databaseBuilder(
        context,
        DataBaseLocal::class.java,
        "rick-and-morty-database"
    ).build()
}