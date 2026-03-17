package com.jgcb.rickandmorty.data.repository

import com.jgcb.rickandmorty.data.local.entity.CharactersListEntity
import com.jgcb.rickandmorty.data.local.entity.PaginationInfoEntity
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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.take
import javax.inject.Inject
import kotlin.collections.map

/**
 * Modified by @Juan Gabriel Corrales on 05/03/2026.
 */

class CharactersRepositoryImpl @Inject constructor(
        private val databaseLocalResource: DatabaseLocalResource,
        private val networkDataSource: NetworkDataSource
) : CharactersRepository {

    /**
     * Retorna un Flow que emite continuamente todos los personajes de la BBDD.
     * Si la BBDD está vacía, carga la primera página automáticamente.
     */
    override fun getCharacters(): Flow<List<Character>> {
        return databaseLocalResource
            .getAllCharacters()
            .onStart {
                // Verificar si necesitamos cargar datos iniciales
                val characters = databaseLocalResource.getAllCharactersSync()
                if (characters.isEmpty()) {
                    // Primera vez, cargar página 1
                    fetchCharacters(page = 1)
                }
            }
            .map { characterEntities ->
                characterEntities.map { it.asExternalModel() }
            }
    }

    override suspend fun loadMoreCharacters(): Result<Unit> {
        return try {
            // Obtener la info de paginación actual
            val paginationInfo = databaseLocalResource.getPaginationInfoSync()

            if (paginationInfo == null) {
                // No hay info de paginación, cargar página 1
                fetchCharacters(page = 1)
                return Result.success(Unit)
            }

            if (!paginationInfo.hasNextPage) {
                // No hay más páginas para cargar
                return Result.success(Unit)
            }

            val nextPage = paginationInfo.currentPage + 1
            fetchCharacters(page = nextPage)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun refreshCharacters(): Result<Unit> {
        return try {
            // Solo resetear la metadata de paginación, NO borrar personajes
            databaseLocalResource.clearPaginationInfo()
            // Volver a cargar página 1 (esto actualizará los personajes existentes)
            fetchCharacters(page = 1)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Obtiene y persiste personajes de una página específica.
     * Los personajes se insertan o actualizan en la BBDD (nunca se borran).
     */
    private suspend fun fetchCharacters(page: Int) {
        // Obtener respuesta de la red
        val response: CharactersListResponse = networkDataSource.fetchNetworkCharacters(page)

        // Mapear a entidad
        val entity: CharactersListEntity = response.asEntity()

        // Persistir personajes en BD (UPSERT - insertar o actualizar)
        // Room hará REPLACE si el personaje ya existe (mismo ID)
        databaseLocalResource.insertCharacters(entity.characters)

        // Guardar metadata de paginación
        val paginationInfo = PaginationInfoEntity(
            id = 0,
            currentPage = page,
            totalPages = response.infoResponse.pages,
            hasNextPage = response.infoResponse.next != null
        )
        databaseLocalResource.insertPaginationInfo(paginationInfo)
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