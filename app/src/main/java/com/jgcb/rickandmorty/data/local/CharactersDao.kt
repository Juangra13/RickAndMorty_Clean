package com.jgcb.rickandmorty.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jgcb.rickandmorty.data.model.Character

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023.
 */

@Dao
interface CharactersDao {

    @Query("SELECT * FROM characters")
    fun getAllCharacters() : LiveData<List<Character>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<Character>)

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id: Int): LiveData<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character)
}