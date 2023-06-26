package com.jgcb.rickandmorty.domain.repository

import androidx.lifecycle.LiveData
import com.jgcb.rickandmorty.data.model.Character
import com.jgcb.rickandmorty.utils.Resource

interface CharactersRepository {

    fun getCharacters(): LiveData<Resource<List<Character>>>

    fun getCharacter(id: Int): LiveData<Resource<Character>>
}