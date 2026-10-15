package com.jgcb.rickandmorty.data.local.repository

import com.jgcb.rickandmorty.data.local.entity.CharacterEntity
import com.jgcb.rickandmorty.data.local.entity.PaginationInfoEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseLocalResource {

        fun getAllCharacters(): Flow<List<CharacterEntity>>

        suspend fun getAllCharactersSync(): List<CharacterEntity>

        suspend fun insertCharacters(characters: List<CharacterEntity>)

        suspend fun clearAllCharacters()

        fun getCharacter(id: Int): Flow<CharacterEntity>

        suspend fun getCharacterSync(id: Int): CharacterEntity?

        suspend fun insertCharacter(character: CharacterEntity)

        // Nuevos métodos para paginación
        fun getPaginationInfo(): Flow<PaginationInfoEntity?>

        suspend fun getPaginationInfoSync(): PaginationInfoEntity?

        suspend fun insertPaginationInfo(info: PaginationInfoEntity)

        suspend fun clearPaginationInfo()
}