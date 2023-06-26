package com.jgcb.rickandmorty.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.jgcb.rickandmorty.data.model.Character
import com.jgcb.rickandmorty.domain.usecase.GetCharacterForIdUseCase
import com.jgcb.rickandmorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by @Juan Gabriel Corrales on 22/07/2023.
 */

@HiltViewModel
class CharactersDetailViewModel @Inject constructor(
        private val useCase: GetCharacterForIdUseCase
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _character = _id.switchMap { id ->
        useCase.getCharacter(id)
    }

    val character: LiveData<Resource<Character>> = _character

    fun start(characterId: Int) {
        _id.value = characterId
    }
}