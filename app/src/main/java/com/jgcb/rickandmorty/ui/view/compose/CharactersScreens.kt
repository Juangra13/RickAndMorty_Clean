package com.jgcb.rickandmorty.ui.view.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.jgcb.rickandmorty.domain.model.Character
import com.jgcb.rickandmorty.ui.viewmodels.CharacterDetailViewState
import com.jgcb.rickandmorty.ui.viewmodels.CharactersDetailViewModel
import com.jgcb.rickandmorty.ui.viewmodels.CharactersListViewModel
import com.jgcb.rickandmorty.ui.viewmodels.CharactersListViewState

@Composable
fun CharactersListScreen(
    viewModel: CharactersListViewModel = hiltViewModel(),
    onNavigateToDetail: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState(initial = CharactersListViewState.Loading)

    if (state is CharactersListViewState.Loading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else if (state is CharactersListViewState.Error) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Error: ${(state as CharactersListViewState.Error).throwable?.localizedMessage ?: "unknown" }")
        }
    } else if (state is CharactersListViewState.Success) {
        val characters = (state as CharactersListViewState.Success).characters
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(characters) { character ->
                CharacterListItem(character = character, onClick = { onNavigateToDetail(character.id) })
            }
        }
    } else {
        // no-op
    }
}

@Composable
private fun CharacterListItem(character: Character, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier.padding(end = 8.dp),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = character.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = character.species, style = MaterialTheme.typography.bodySmall)
        }
        Text(text = character.status, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun CharactersDetailScreen(
    characterId: Int,
    viewModel: CharactersDetailViewModel = hiltViewModel()
) {
    // Trigger load when composable is first composed
    androidx.compose.runtime.LaunchedEffect(characterId) {
        viewModel.start(characterId)
    }

    val state by viewModel.state.collectAsState(initial = CharacterDetailViewState.Loading)

    if (state is CharacterDetailViewState.Loading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else if (state is CharacterDetailViewState.Error) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Error: ${(state as CharacterDetailViewState.Error).throwable?.localizedMessage ?: "unknown" }")
        }
    } else if (state is CharacterDetailViewState.Success) {
        val character = (state as CharacterDetailViewState.Success).character
        CharacterDetailContent(character)
    } else {
        // no-op
    }
}

@Composable
private fun CharacterDetailContent(character: Character) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = character.name, style = MaterialTheme.typography.headlineMedium)
        Text(text = "Species: ${character.species}")
        Text(text = "Status: ${character.status}")
        Text(text = "Gender: ${character.gender}")
    }
}
