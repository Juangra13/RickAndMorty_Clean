package com.jgcb.rickandmorty.data.local.repository

import com.jgcb.rickandmorty.data.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseLocalResource {

        fun getAllCharacters(): Flow<List<CharacterEntity>>

        suspend fun insertCharacters(characters: List<CharacterEntity>)

        fun getCharacter(id: Int): Flow<CharacterEntity>

        suspend fun insertCharacter(character: CharacterEntity)
}