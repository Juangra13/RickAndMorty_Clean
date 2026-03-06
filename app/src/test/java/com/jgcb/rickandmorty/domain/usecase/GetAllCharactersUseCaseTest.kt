package com.jgcb.rickandmorty.domain.usecase

import com.jgcb.rickandmorty.data.repository.CharactersRepositoryImpl
import com.jgcb.rickandmorty.domain.repository.CharactersRepository
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before


class GetAllCharactersUseCaseTest {

    private lateinit var repositoryImpl: CharactersRepository
    private lateinit var getAllCharactersUseCase: GetAllCharactersUseCase

    @Before
    fun setUp() {
        repositoryImpl = mockk<CharactersRepositoryImpl>()
        getAllCharactersUseCase = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        unmockkAll()
    }
}