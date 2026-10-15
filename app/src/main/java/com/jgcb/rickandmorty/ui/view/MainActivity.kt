package com.jgcb.rickandmorty.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mvicomposeproject.ui.theme.MVIComposeProjectTheme
import com.jgcb.rickandmorty.ui.view.compose.CharactersDetailScreen
import com.jgcb.rickandmorty.ui.view.compose.CharactersListScreen
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
    val canNavigateBack = backStackEntry?.destination?.route != "list" && navController.previousBackStackEntry != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val currentRoute = backStackEntry?.destination?.route
                                if (currentRoute == "list") {
                                    // Fuerza recrear la pantalla 'list' para volver a ejecutar la carga inicial
                                    navController.navigate("list") {
                                        popUpTo("list") { inclusive = true }
                                    }
                                } else {
                                    // Si no estamos en 'list', navegamos a 'list'
                                    navController.navigate("list")
                                }
                            }
                        , contentAlignment = Alignment.Center) {
                        Text(text = "Rick & Morty")
                } },
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Image(painter = painterResource(android.R.drawable.ic_menu_revert), contentDescription = "Volver")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "list",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("list") {
                CharactersListScreen(onNavigateToDetail = { id -> navController.navigate("detail/$id") })
            }
            composable("detail/{id}") { backStack ->
                val idArg = backStack.arguments?.getString("id")
                val id = idArg?.toIntOrNull() ?: 0
                CharactersDetailScreen(characterId = id)
            }
        }
    }
}
