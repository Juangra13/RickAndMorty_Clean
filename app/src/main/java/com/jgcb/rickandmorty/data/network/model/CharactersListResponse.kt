package com.jgcb.rickandmorty.data.network.model

import com.jgcb.rickandmorty.data.local.entity.CharactersListEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Modified by @Juan Gabriel Corrales on 03/03/2026.
 */

@Serializable
data class CharactersListResponse(
        @SerialName("info")
        val infoResponse: InfoResponse,
        @SerialName("results")
        val characters: List<CharacterResponse>
)

fun CharactersListResponse.asEntity() = CharactersListEntity(
    info = infoResponse.asEntity(),
    characters = characters.map { it.asEntity() }
)