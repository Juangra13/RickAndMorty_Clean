package com.jgcb.rickandmorty.domain.repository

import com.jgcb.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getCharacters(): Flow<List<Character>>
    suspend fun loadMoreCharacters(): Result<Unit>

    fun getCharacter(id: Int): Flow<Character>
    suspend fun refreshCharacters(): Result<Unit>
}