package com.jgcb.rickandmorty.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jgcb.rickandmorty.data.model.Character

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023.
 */

@Database(entities = [Character::class], version = 1, exportSchema = false)
abstract class DataBaseLocal : RoomDatabase() {

    companion object {
        @Volatile private var dataBase: DataBaseLocal? = null

        fun getDataBaseLocal(context: Context): DataBaseLocal =
            dataBase ?: synchronized(this) { dataBase ?: buildDataBaseLocal(context).also { dataBase = it } }

        private fun buildDataBaseLocal(appContext: Context) =
            Room.databaseBuilder(appContext, DataBaseLocal::class.java, "characters")
                .fallbackToDestructiveMigration().build()
    }

    abstract fun charactersDao(): CharactersDao
}