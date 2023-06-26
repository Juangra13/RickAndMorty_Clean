package com.jgcb.rickandmorty.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.jgcb.rickandmorty.domain.usecase.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023
 */

@HiltViewModel
class CharactersListViewModel @Inject constructor(
        private val useCase: GetAllCharactersUseCase
) : ViewModel(){

    val characters = useCase.getAllCharacter()
}


