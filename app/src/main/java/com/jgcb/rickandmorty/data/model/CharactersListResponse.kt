package com.jgcb.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023.
 */

data class CharactersListResponse(
        @SerializedName("info")
        val info: Info,
        @SerializedName("results")
        val characters: List<Character>
)