package com.jgcb.rickandmorty.data.network.repository

import com.jgcb.rickandmorty.data.network.model.CharacterResponse
import com.jgcb.rickandmorty.data.network.model.CharactersListResponse

interface NetworkDataSource {
        suspend fun fetchNetworkCharacters(): CharactersListResponse
        suspend fun fetchNetworkCharacter(id: Int): CharacterResponse
}