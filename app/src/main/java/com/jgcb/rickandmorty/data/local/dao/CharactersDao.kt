package com.jgcb.rickandmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jgcb.rickandmorty.data.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

/**
 * Modified by @Juan Gabriel Corrales on 03/03/2026.
 */

@Dao
interface CharactersDao {

    @Query("SELECT * FROM characters_entity")
    fun getAllCharacters() : Flow<List<CharacterEntity>>

    @Upsert
    suspend fun insertAllCharacters(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters_entity WHERE id = :id")
    fun getCharacter(id: Int): Flow<CharacterEntity>

    @Upsert
    suspend fun insertCharacter(character: CharacterEntity)
}