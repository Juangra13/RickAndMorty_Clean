package com.jgcb.rickandmorty.data.network.model

import com.jgcb.rickandmorty.data.local.entity.CharacterEntity
import kotlinx.serialization.Serializable

/**
 * Modified by @Juan Gabriel Corrales on 03/03/2026.
 */
@Serializable
data class CharacterResponse(
        val created: String,
        val gender: String,
        val id: Int,
        val image: String,
        val name: String,
        val species: String,
        val status: String,
        val type: String,
        val url: String
)

fun CharacterResponse.asEntity() = CharacterEntity(
    created =  created,
    gender = gender,
    id = id,
    image = image,
    name = name,
    species = species,
    status = status,
    type = type,
    url = url
)