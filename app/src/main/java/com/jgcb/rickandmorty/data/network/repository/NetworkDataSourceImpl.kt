package com.jgcb.rickandmorty.data.network.repository

import com.jgcb.rickandmorty.data.di.IoDispatcher
import com.jgcb.rickandmorty.data.network.api.CharactersApi
import com.jgcb.rickandmorty.data.network.model.CharacterResponse
import com.jgcb.rickandmorty.data.network.model.CharactersListResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val api: CharactersApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): NetworkDataSource {
    override suspend fun fetchNetworkCharacters(): CharactersListResponse {
        return withContext(dispatcher) {
            api.getAllCharacters()
        }
    }

    override suspend fun fetchNetworkCharacter(id: Int): CharacterResponse {
        return withContext(dispatcher) {
            api.getCharacter(id)
        }
    }

}