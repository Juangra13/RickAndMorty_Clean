package com.jgcb.rickandmorty.data.local.entity

data class CharactersListEntity(
        val info: InfoEntity,
        val characters: List<CharacterEntity>
)
