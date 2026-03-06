package com.jgcb.rickandmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jgcb.rickandmorty.data.local.dao.CharactersDao
import com.jgcb.rickandmorty.data.local.entity.CharacterEntity

/**
 * Modified by @Juan Gabriel Corrales on 03/03/2026.
 */

@Database(
    entities = [
        CharacterEntity::class],
    version = 1,
    exportSchema = true
)
abstract class DataBaseLocal : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao
}