package com.example.zplay.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zplay.ui.favorites.PantallaFavoritos
import com.example.zplay.ui.Home.PantallaHome
import com.example.zplay.ui.profile.PantallaPerfil

enum class AppDestinations {
    Home,
    PlayList,
    Setings
}

@Composable
fun ZPlayApp() {

    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.Home) }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp)   // 🔥 pantalla completa
    ) { _ ->

        val pad = Modifier.padding(0.dp)

        when (currentDestination) {
            AppDestinations.Home -> PantallaHome(modifier = pad)
            AppDestinations.PlayList -> PantallaFavoritos(modifier = pad)
            AppDestinations.Setings -> PantallaPerfil(modifier = pad)
        }
    }
}
