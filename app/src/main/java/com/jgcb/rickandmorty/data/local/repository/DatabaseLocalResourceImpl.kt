package com.jgcb.rickandmorty.data.local.repository

import com.jgcb.rickandmorty.data.di.IoDispatcher
import com.jgcb.rickandmorty.data.local.dao.CharactersDao
import com.jgcb.rickandmorty.data.local.dao.PaginationInfoDao
import com.jgcb.rickandmorty.data.local.entity.CharacterEntity
import com.jgcb.rickandmorty.data.local.entity.PaginationInfoEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DatabaseLocalResourceImpl @Inject constructor(
    private val charactersDao: CharactersDao,
    private val paginationInfoDao: PaginationInfoDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : DatabaseLocalResource {

    override fun getAllCharacters(): Flow<List<CharacterEntity>> {
        return charactersDao.getAllCharacters().flowOn(dispatcher)
    }

    override suspend fun getAllCharactersSync(): List<CharacterEntity> =
        charactersDao.getAllCharactersSync()

    override suspend fun insertCharacters(characters: List<CharacterEntity>) {
        withContext(dispatcher) {
            charactersDao.insertAllCharacters(characters)
        }
    }

    override fun getCharacter(id: Int): Flow<CharacterEntity> {
        return charactersDao.getCharacter(id).flowOn(dispatcher)
    }

    override suspend fun getCharacterSync(id: Int): CharacterEntity? {
        return charactersDao.getCharacterSync(id)
    }

    override suspend fun insertCharacter(character: CharacterEntity) {
        withContext(dispatcher) {
            charactersDao.insertCharacter(character)
        }
    }

    override suspend fun clearAllCharacters() {
        withContext(dispatcher) {
            charactersDao.clearAllCharacters()
        }
    }

    override fun getPaginationInfo(): Flow<PaginationInfoEntity?> =
        paginationInfoDao.getPaginationInfo()

    override suspend fun getPaginationInfoSync(): PaginationInfoEntity? =
        paginationInfoDao.getPaginationInfoSync()

    override suspend fun insertPaginationInfo(info: PaginationInfoEntity) =
        paginationInfoDao.insertPaginationInfo(info)

    override suspend fun clearPaginationInfo() =
        paginationInfoDao.clearPaginationInfo()
}