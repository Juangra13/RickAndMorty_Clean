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
    data class Success(
            val characters: List<Character>,
            val isLoadingMore: Boolean = false,
            val isRefreshing: Boolean = false
    ) : CharactersListViewState()
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
            useCase.getAllCharacter().collect { result ->
                result.fold(
                    onSuccess = { list ->
                        val currentState = _state.value
                        val isLoadingMore = currentState is CharactersListViewState.Success && currentState.isLoadingMore
                        val isRefreshing = currentState is CharactersListViewState.Success && currentState.isRefreshing

                        _state.value = CharactersListViewState.Success(
                            characters = list,
                            isLoadingMore = false,
                            isRefreshing = false
                        )
                    },
                    onFailure = { throwable ->
                        _state.value = CharactersListViewState.Error(throwable)
                    }
                )
            }
        }
    }

    /**
     * Carga la siguiente página de personajes.
     */
    fun loadMore() {
        val currentState = _state.value
        if (currentState is CharactersListViewState.Success && !currentState.isLoadingMore) {
            _state.value = currentState.copy(isLoadingMore = true)

            viewModelScope.launch {
                useCase.loadMore().fold(
                    onSuccess = {
                        // El Flow de observeCharacters() actualizará automáticamente el estado
                    },
                    onFailure = { throwable ->
                        _state.value = CharactersListViewState.Error(throwable)
                    }
                )
            }
        }
    }

    /**
     * Resetea la paginación y recarga desde la página 1.
     * Los personajes en BBDD se mantienen y se actualizan.
     */
    fun refresh() {
        val currentState = _state.value
        // Permitir refresh desde cualquier estado excepto si ya está refrescando
        val isAlreadyRefreshing = currentState is CharactersListViewState.Success && currentState.isRefreshing

        if (!isAlreadyRefreshing) {
            // Si estamos en Success, marcamos isRefreshing
            if (currentState is CharactersListViewState.Success) {
                _state.value = currentState.copy(isRefreshing = true)
            } else {
                // Si estamos en Error o Loading, volver a Loading
                _state.value = CharactersListViewState.Loading
            }

            viewModelScope.launch {
                useCase.refresh().fold(
                    onSuccess = {
                        // El Flow de observeCharacters() actualizará automáticamente el estado
                    },
                    onFailure = { throwable ->
                        _state.value = CharactersListViewState.Error(throwable)
                    }
                )
            }
        }
    }
}
