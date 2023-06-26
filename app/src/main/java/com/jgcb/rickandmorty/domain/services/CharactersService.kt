package com.jgcb.rickandmorty.domain.services

import com.jgcb.rickandmorty.data.model.Character
import com.jgcb.rickandmorty.data.model.CharactersListResponse
import com.jgcb.rickandmorty.utils.ContentConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023.
 */

interface CharactersService {
    @GET(ContentConstants.CHARACTERS_URL_PATH)
    suspend fun getAllCharacters(): Response<CharactersListResponse>

    @GET(ContentConstants.CHARACTER_ID_URL_PATH)
    suspend fun getCharacter(@Path(ContentConstants.VALUE_ID) id: Int): Response<Character>
}