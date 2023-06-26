package com.jgcb.rickandmorty.domain.usecase

import androidx.lifecycle.LiveData
import com.jgcb.rickandmorty.data.model.Character
import com.jgcb.rickandmorty.domain.repository.CharactersRepository
import com.jgcb.rickandmorty.utils.Resource
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
        private val repository: CharactersRepository
) {
    fun getAllCharacter(): LiveData<Resource<List<Character>>> {
        return repository.getCharacters()
    }
}