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

import org.junit.Assert.*

class GetCharacterForIdUseCaseTest {

    private lateinit var repositoryImpl: CharactersRepository
    private lateinit var getCharacterForIdUseCase: GetCharacterForIdUseCase

    @Before
    fun setUp() {
        repositoryImpl = mockk<CharactersRepositoryImpl>()
        getCharacterForIdUseCase = mockk(relaxed = true)
    }

    @Test
    fun getCharacter() {
        val liveData: LiveData<Resource<Character>> = mockk()

        every { repositoryImpl.getCharacter(any()) } returns liveData
        every { getCharacterForIdUseCase.getCharacter(any()) } returns liveData
        val result = getCharacterForIdUseCase.getCharacter(1)

        Assert.assertEquals(result, liveData)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        unmockkAll()
    }
}