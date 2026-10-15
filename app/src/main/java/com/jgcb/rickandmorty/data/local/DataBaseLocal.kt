package com.jgcb.rickandmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jgcb.rickandmorty.data.local.dao.CharactersDao
import com.jgcb.rickandmorty.data.local.dao.PaginationInfoDao
import com.jgcb.rickandmorty.data.local.entity.CharacterEntity
import com.jgcb.rickandmorty.data.local.entity.PaginationInfoEntity

/**
 * Modified by @Juan Gabriel Corrales on 03/03/2026.
 */

@Database(
    entities = [
        CharacterEntity::class,
        PaginationInfoEntity::class],
    version = 2,
    exportSchema = false
)
abstract class DataBaseLocal : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao
    abstract fun paginationInfoDao(): PaginationInfoDao
}