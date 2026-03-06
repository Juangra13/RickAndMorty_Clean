package com.jgcb.rickandmorty.domain.usecase

import com.jgcb.rickandmorty.domain.model.Character
import com.jgcb.rickandmorty.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCharacterForIdUseCase @Inject constructor(
        private val repository: CharactersRepository
) {
    fun getCharacter(id: Int): Flow<Result<Character>> {
        return repository
            .getCharacter(id)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}