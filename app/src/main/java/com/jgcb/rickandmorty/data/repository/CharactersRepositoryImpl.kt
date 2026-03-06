package com.jgcb.rickandmorty.data.repository

import com.jgcb.rickandmorty.data.local.entity.CharactersListEntity
import com.jgcb.rickandmorty.data.local.entity.asExternalModel
import com.jgcb.rickandmorty.data.local.repository.DatabaseLocalResource
import com.jgcb.rickandmorty.data.local.utils.EmptyCacheException
import com.jgcb.rickandmorty.data.network.model.CharactersListResponse
import com.jgcb.rickandmorty.data.network.model.asEntity
import com.jgcb.rickandmorty.data.network.repository.NetworkDataSource
import com.jgcb.rickandmorty.domain.model.Character
import com.jgcb.rickandmorty.domain.repository.CharactersRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.take
import javax.inject.Inject

/**
 * Modified by @Juan Gabriel Corrales on 05/03/2026.
 */

class CharactersRepositoryImpl @Inject constructor(
        private val databaseLocalResource: DatabaseLocalResource,
        private val networkDataSource: NetworkDataSource
) : CharactersRepository {

    override fun getCharacters(): Flow<List<Character>> {
        return databaseLocalResource
            .getAllCharacters()
            .map { characterEntities ->
                if (characterEntities.isEmpty()) {
                    throw EmptyCacheException()
                }
                delay(1000)
                characterEntities.map { it.asExternalModel() }
            }
            .retryWhen { cause, attempt ->
                if (cause is EmptyCacheException && attempt < 1) {
                    fetchCharacters()
                    true
                } else {
                    false
                }
            }
            .take(1)
    }

    private suspend fun fetchCharacters(): List<Character> {
        // Obtener y tipar explícitamente la primera emisión desde la red
        val response: CharactersListResponse =
            networkDataSource.fetchNetworkCharacters()
        // Mapear a la entidad (asEntity debe ser extensión sobre CharactersListResponse)
        val entity: CharactersListEntity = response.asEntity()
        // Persistir en BD (insertCharacters debe aceptar List<CharacterEntity>)
        databaseLocalResource.insertCharacters(entity.characters)
        // Mapear a modelo de dominio y devolver la lista
        return entity.characters.map { it.asExternalModel() }
    }

    override fun getCharacter(id: Int): Flow<Character> {
        return databaseLocalResource
            .getCharacter(id)
            .map { characterEntity ->
                characterEntity.asExternalModel()
            }
            .retryWhen { cause, attempt ->
                if (cause is NullPointerException && attempt < 1) {
                    fetchCharacter(id)
                    true
                } else {
                    false
                }
            }
            .take(1)
    }

    private suspend fun fetchCharacter(id: Int): Character {
        return networkDataSource.fetchNetworkCharacter(id)
            .asEntity()
            .also { databaseLocalResource.insertCharacter(it) }
            .asExternalModel()
    }

}