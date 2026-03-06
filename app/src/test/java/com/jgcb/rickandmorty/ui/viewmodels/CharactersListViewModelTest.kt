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
import com.jgcb.rickandmorty.domain.usecase.GetAllCharactersUseCase

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersListViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    @Test
    fun stateEmitsSuccessWhenUsecaseReturnsList() = runTest(dispatcher) {
        val fakeCharacters = listOf(
            Character(
                created = "2020-01-01",
                gender = "Male",
                id = 1,
                image = "url",
                name = "Rick",
                species = "Human",
                status = "Alive",
                type = "",
                url = "url"
            )
        )
        val useCase = mockk<GetAllCharactersUseCase>()
        coEvery { useCase.getAllCharacter() } returns flow { emit(Result.success(fakeCharacters)) }

        val vm = CharactersListViewModel(useCase)

        vm.state.test {
            val item = awaitItem()
            // initial is Loading or first emission; wait for success
            val next = awaitItem()
            when (next) {
                is CharactersListViewState.Success -> assert(next.characters.size == 1)
                is CharactersListViewState.Error -> throw AssertionError("Expected success but got error")
                else -> Unit
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun stateEmitsErrorWhenUsecaseFails() = runTest(dispatcher) {
        val useCase = mockk<GetAllCharactersUseCase>()
        coEvery { useCase.getAllCharacter() } returns flow { emit(Result.failure<List<Character>>(RuntimeException("fail"))) }

        val vm = CharactersListViewModel(useCase)

        vm.state.test {
            val item = awaitItem()
            val next = awaitItem()
            when (next) {
                is CharactersListViewState.Error -> assert(next.throwable?.message == "fail")
                else -> throw AssertionError("Expected error but got success")
            }
            cancelAndIgnoreRemainingEvents()
        }
    }
}
