package com.jgcb.rickandmorty.ui.viewmodels

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.jgcb.rickandmorty.domain.model.Character
import com.jgcb.rickandmorty.domain.usecase.GetCharacterForIdUseCase

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersDetailViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    @Test
    fun stateEmitsSuccessWhenUsecaseReturnsCharacter() = runTest(dispatcher) {
        val fakeCharacter = Character(
            created = "2020-01-01",
            gender = "Male",
            id = 1,
            image = "url",
            name = "Morty",
            species = "Human",
            status = "Alive",
            type = "",
            url = "url"
        )
        val useCase = mockk<GetCharacterForIdUseCase>()
        coEvery { useCase.getCharacter(1) } returns flow { emit(Result.success(fakeCharacter)) }

        val vm = CharactersDetailViewModel(useCase)
        vm.start(1)

        vm.state.test {
            val item = awaitItem()
            val next = awaitItem()
            when (next) {
                is CharacterDetailViewState.Success -> assert((next).character.name == "Morty")
                else -> throw AssertionError("Expected success but got error")
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun stateEmitsErrorWhenUsecaseFails() = runTest(dispatcher) {
        val useCase = mockk<GetCharacterForIdUseCase>()
        coEvery { useCase.getCharacter(2) } returns flow { emit(Result.failure<Character>(RuntimeException("fail"))) }

        val vm = CharactersDetailViewModel(useCase)
        vm.start(2)

        vm.state.test {
            val item = awaitItem()
            val next = awaitItem()
            when (next) {
                is CharacterDetailViewState.Error -> assert(next.throwable?.message == "fail")
                else -> throw AssertionError("Expected error but got success")
            }
            cancelAndIgnoreRemainingEvents()
        }
    }
}
