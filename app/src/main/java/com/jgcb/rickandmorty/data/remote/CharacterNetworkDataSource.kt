package com.jgcb.rickandmorty.data.remote

import com.jgcb.rickandmorty.domain.services.CharactersService
import javax.inject.Inject

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023.
 */

class CharacterNetworkDataSource @Inject constructor(
        private val charactersService: CharactersService
) : BaseDataNetwork() {

    suspend fun getCharacters() = getResponse { charactersService.getAllCharacters() }

    suspend fun getCharacter(id: Int) = getResponse { charactersService.getCharacter(id) }
}