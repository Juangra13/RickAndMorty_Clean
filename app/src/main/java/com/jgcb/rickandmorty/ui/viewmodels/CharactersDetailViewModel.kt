package com.jgcb.rickandmorty.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgcb.rickandmorty.domain.model.Character
import com.jgcb.rickandmorty.domain.usecase.GetCharacterForIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Modified by @Juan Gabriel Corrales on 05/03/2026.
 */

sealed class CharacterDetailViewState {
    object Loading : CharacterDetailViewState()
    data class Success(val character: Character) : CharacterDetailViewState()
    data class Error(val throwable: Throwable?) : CharacterDetailViewState()
}

@HiltViewModel
class CharactersDetailViewModel @Inject constructor(
        private val useCase: GetCharacterForIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterDetailViewState>(CharacterDetailViewState.Loading)
    val state: StateFlow<CharacterDetailViewState> = _state.asStateFlow()

    fun start(characterId: Int) {
        _state.value = CharacterDetailViewState.Loading
        viewModelScope.launch {
            useCase.getCharacter(characterId).collect { result ->
                result.fold(onSuccess = { character ->
                    _state.value = CharacterDetailViewState.Success(character)
                }, onFailure = { throwable ->
                    _state.value = CharacterDetailViewState.Error(throwable)
                })
            }
        }
    }
}