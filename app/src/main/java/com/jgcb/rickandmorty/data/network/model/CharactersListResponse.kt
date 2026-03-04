package com.jgcb.rickandmorty.data.network.model

import com.google.gson.annotations.SerializedName
import com.jgcb.rickandmorty.data.local.entity.CharactersListEntity
import com.jgcb.rickandmorty.domain.model.Character
import kotlinx.serialization.Serializable

/**
 * Modified by @Juan Gabriel Corrales on 03/03/2026.
 */

@Serializable
data class CharactersListResponse(
        @SerializedName("info")
        val infoResponse: InfoResponse,
        @SerializedName("results")
        val characters: List<CharacterResponse>
)

fun CharactersListResponse.asEntity() = CharactersListEntity(
    info = infoResponse.asEntity(),
    characters = characters.map { it.asEntity() }
)