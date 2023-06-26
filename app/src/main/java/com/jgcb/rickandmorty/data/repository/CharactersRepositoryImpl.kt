package com.jgcb.rickandmorty.data.repository

import com.jgcb.rickandmorty.data.local.CharactersDao
import com.jgcb.rickandmorty.data.remote.CharacterNetworkDataSource
import com.jgcb.rickandmorty.domain.repository.CharactersRepository
import com.jgcb.rickandmorty.utils.localOrNetworkOperation
import javax.inject.Inject

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023.
 */

class CharactersRepositoryImpl @Inject constructor(
        private val networkDataSource: CharacterNetworkDataSource,
        private val charactersDao: CharactersDao
) : CharactersRepository {
    override fun getCharacters() = localOrNetworkOperation(
        databaseQuery = { charactersDao.getAllCharacters() },
        networkCall = { networkDataSource.getCharacters() },
        saveCallResult = { charactersDao.insertAllCharacters(it.characters) }
    )

    override fun getCharacter(id: Int) = localOrNetworkOperation(
        databaseQuery = {  charactersDao.getCharacter(id) },
        networkCall = { networkDataSource.getCharacter(id) },
        saveCallResult = { charactersDao.insertCharacter(it)}
    )

}