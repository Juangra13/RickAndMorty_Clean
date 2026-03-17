package com.jgcb.rickandmorty.ui.view.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    val listState = rememberLazyListState()

    // Detectar cuando llegamos al final de la lista
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                // Cargar más cuando estamos a 3 items del final
                if (lastVisibleIndex != null && lastVisibleIndex >= totalItems - 3) {
                    viewModel.loadMore()
                }
            }
    }

    when (state) {
        is CharactersListViewState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        is CharactersListViewState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Error: ${(state as CharactersListViewState.Error).throwable?.localizedMessage ?: "unknown"}")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.refresh() }) {
                    Text("Reintentar")
                }
            }
        }

        is CharactersListViewState.Success -> {
            val successState = state as CharactersListViewState.Success
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                items(successState.characters) { character ->
                    CharacterListItem(
                        character = character,
                        onClick = { onNavigateToDetail(character.id) }
                    )
                }

                // Mostrar indicador de carga al final si estamos cargando más
                if (successState.isLoadingMore) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterListItem(character: Character, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
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
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = character.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = character.species, style = MaterialTheme.typography.bodySmall)
            }
            Text(
                text = when(character.status) {
                    "Alive" -> "😉"
                    "Dead" -> "☠️"
                    else -> "❓"
                },
                modifier = Modifier.padding(start = 8.dp))
        }
    }
}

@Composable
fun CharactersDetailScreen(
    characterId: Int,
    viewModel: CharactersDetailViewModel = hiltViewModel()
) {
    // Trigger load when composable is first composed
    LaunchedEffect(characterId) {
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
