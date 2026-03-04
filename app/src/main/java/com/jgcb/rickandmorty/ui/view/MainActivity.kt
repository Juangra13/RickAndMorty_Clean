package com.jgcb.rickandmorty.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mvicomposeproject.ui.theme.MVIComposeProjectTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Modified by @Juan Gabriel Corrales on 04/03/2026.
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVIComposeProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = backStackEntry?.destination?.route != "home" && navController.previousBackStackEntry != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Rick & Morty") },
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                        }
                    } else null
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                // Reemplazar por tu composable real de lista de personajes
                HomeScreen(onNavigateToDetail = { id -> navController.navigate("detail/$id") })
            }
            composable("detail/{id}") { backStack ->
                // Reemplazar por tu composable real de detalle de personaje
                val id = backStack.arguments?.getString("id")
                DetailScreen(characterId = id)
            }
        }
    }
}

/* Composables de ejemplo: reemplazar con tus implementaciones reales */
@Composable
private fun HomeScreen(onNavigateToDetail: (String) -> Unit) {
    Text(text = "Pantalla principal (reemplazar)")
}

@Composable
private fun DetailScreen(characterId: String?) {
    Text(text = "Detalle personaje: ${characterId ?: "n/a"} (reemplazar)")
}