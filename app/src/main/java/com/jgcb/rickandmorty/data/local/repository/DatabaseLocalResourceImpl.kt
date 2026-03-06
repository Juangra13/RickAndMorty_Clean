package com.jgcb.rickandmorty.data.local.repository

import com.jgcb.rickandmorty.data.di.IoDispatcher
import com.jgcb.rickandmorty.data.local.dao.CharactersDao
import com.jgcb.rickandmorty.data.local.entity.CharacterEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DatabaseLocalResourceImpl @Inject constructor(
    private val charactersDao: CharactersDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : DatabaseLocalResource {

    override fun getAllCharacters(): Flow<List<CharacterEntity>> {
        return charactersDao.getAllCharacters().flowOn(dispatcher)
    }

    override suspend fun insertCharacters(characters: List<CharacterEntity>) {
        withContext(dispatcher) {
            charactersDao.insertAllCharacters(characters)
        }
    }

    override fun getCharacter(id: Int): Flow<CharacterEntity> {
        return charactersDao.getCharacter(id).flowOn(dispatcher)
    }

    override suspend fun insertCharacter(character: CharacterEntity) {
        withContext(dispatcher) {
            charactersDao.insertCharacter(character)
        }
    }
}