package com.jgcb.rickandmorty.data.network.api

import com.jgcb.rickandmorty.data.network.model.CharacterResponse
import com.jgcb.rickandmorty.data.network.model.CharactersListResponse
import com.jgcb.rickandmorty.utils.ContentConstants
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Modified by @Juan Gabriel Corrales on 03/03/2026.
 */

interface CharactersApi {
    @GET(ContentConstants.CHARACTERS_URL_PATH)
    suspend fun getAllCharacters(): CharactersListResponse

    @GET(ContentConstants.CHARACTER_ID_URL_PATH)
    suspend fun getCharacter(@Path(ContentConstants.VALUE_ID) id: Int): CharacterResponse
}