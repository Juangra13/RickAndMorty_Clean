package com.jgcb.rickandmorty.domain.model

/**
 * Modified by @Juan Gabriel Corrales on 03/03/2026.
 */

data class Character(
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