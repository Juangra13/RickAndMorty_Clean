package com.jgcb.rickandmorty.domain.usecase

import androidx.lifecycle.LiveData
import com.jgcb.rickandmorty.data.model.Character
import com.jgcb.rickandmorty.data.repository.CharactersRepositoryImpl
import com.jgcb.rickandmorty.domain.repository.CharactersRepository
import com.jgcb.rickandmorty.utils.Resource
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class GetAllCharactersUseCaseTest {

    private lateinit var repositoryImpl: CharactersRepository
    private lateinit var getAllCharactersUseCase: GetAllCharactersUseCase

    @Before
    fun setUp() {
        repositoryImpl = mockk<CharactersRepositoryImpl>()
        getAllCharactersUseCase = mockk(relaxed = true)
    }

    @Test
    fun getAllCharacter() {

        val liveData: LiveData<Resource<List<Character>>> = mockk()

        every { repositoryImpl.getCharacters() } returns liveData
        every { getAllCharactersUseCase.getAllCharacter() } returns liveData
        val result = getAllCharactersUseCase.getAllCharacter()

        Assert.assertEquals(result, liveData)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        unmockkAll()
    }
}