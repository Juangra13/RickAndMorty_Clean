package com.jgcb.rickandmorty.domain.repository

import com.jgcb.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getCharacters(): Flow<List<Character>>

    fun getCharacter(id: Int): Flow<Character>
}