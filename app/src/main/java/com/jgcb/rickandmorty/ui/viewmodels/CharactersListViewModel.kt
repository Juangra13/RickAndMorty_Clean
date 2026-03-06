package com.jgcb.rickandmorty.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgcb.rickandmorty.domain.model.Character
import com.jgcb.rickandmorty.domain.usecase.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Modified by @Juan Gabriel Corrales on 05/03/2026
 */

sealed class CharactersListViewState {
    object Loading : CharactersListViewState()
    data class Success(val characters: List<Character>) : CharactersListViewState()
    data class Error(val throwable: Throwable?) : CharactersListViewState()
}

@HiltViewModel
class CharactersListViewModel @Inject constructor(
        private val useCase: GetAllCharactersUseCase
) : ViewModel(){

    private val _state = MutableStateFlow<CharactersListViewState>(CharactersListViewState.Loading)
    val state: StateFlow<CharactersListViewState> = _state.asStateFlow()

    init {
        observeCharacters()
    }

    private fun observeCharacters() {
        viewModelScope.launch {
            // useCase.getAllCharacter() already returns Flow<Result<List<Character>>>
            useCase.getAllCharacter().collect { result ->
                result.fold(onSuccess = { list ->
                    _state.value = CharactersListViewState.Success(list)
                }, onFailure = { throwable ->
                    _state.value = CharactersListViewState.Error(throwable)
                })
            }
        }
    }

    fun refresh() {
        _state.value = CharactersListViewState.Loading
        observeCharacters()
    }
}
