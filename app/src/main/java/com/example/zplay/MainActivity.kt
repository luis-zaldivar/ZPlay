package com.example.zplay

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.zplay.ui.theme.ZPlayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ZPlayTheme {
                PedirPermisoAudio {
                    ZPlayApp()
                }
            }
        }
    }
}

/* ======================= DESTINOS ======================= */

enum class AppDestinations(
    val label: String,
    val icon: ImageVector
) {
    HOME("Home", Icons.Default.Home),
    FAVORITES("Favorites", Icons.Default.Favorite),
    PROFILE("Profile", Icons.Default.AccountBox)
}

/* ======================= UI PRINCIPAL ======================= */

@Composable
fun ZPlayApp() {

    var currentDestination by rememberSaveable {
        mutableStateOf(AppDestinations.HOME)
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destino ->
                item(
                    icon = { Icon(destino.icon, destino.label) },
                    label = { Text(destino.label) },
                    selected = currentDestination == destino,
                    onClick = { currentDestination = destino }
                )
            }
        }
    ) {
        Scaffold { innerPadding ->
            val contentModifier = Modifier.padding(innerPadding)

            when (currentDestination) {
                AppDestinations.HOME -> PantallaHome(contentModifier)
                AppDestinations.FAVORITES -> PantallaFavoritos(contentModifier)
                AppDestinations.PROFILE -> PantallaPerfil(contentModifier)
            }
        }
    }
}

/* ======================= HOME ======================= */

@Composable
fun PantallaHome(modifier: Modifier = Modifier) {

    var sliderPosition by rememberSaveable { mutableStateOf(0.3f) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("ZPlay")
        Spacer(Modifier.height(16.dp))
        Text("Ahora suena:")
        Text("Nombre de la canción")
        Text("Artista")
        Spacer(Modifier.height(16.dp))

        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(Icons.Default.SkipPrevious, null, Modifier.size(40.dp))
            Icon(Icons.Default.PlayArrow, null, Modifier.size(64.dp))
            Icon(Icons.Default.SkipNext, null, Modifier.size(40.dp))
        }
    }
}

/* ======================= FAVORITOS ======================= */

@Composable
fun PantallaFavoritos(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Tus favoritos")
        Spacer(Modifier.height(16.dp))
        Text("Aquí iría la lista de canciones favoritas 🎵")
    }
}

/* ======================= PERFIL ======================= */

@Composable
fun PantallaPerfil(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Perfil")
        Spacer(Modifier.height(16.dp))
        Text("Nombre de usuario: ZPlayer")
        Spacer(Modifier.height(8.dp))
        Button(onClick = {}) { Text("Ajustes") }
    }
}

/* ======================= PERMISOS ======================= */

@Composable
fun PedirPermisoAudio(onPermisoConcedido: () -> Unit) {

    val context = LocalContext.current

    val permiso = if (Build.VERSION.SDK_INT >= 33)
        android.Manifest.permission.READ_MEDIA_AUDIO
    else
        android.Manifest.permission.READ_EXTERNAL_STORAGE

    val estado = ContextCompat.checkSelfPermission(context, permiso)

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) onPermisoConcedido()
    }

    LaunchedEffect(Unit) {
        if (estado != PackageManager.PERMISSION_GRANTED) {
            launcher.launch(permiso)
        } else {
            onPermisoConcedido()
        }
    }
}

/* ======================= PREVIEW ======================= */

@Preview(showBackground = true)
@Composable
fun PreviewZPlay() {
    ZPlayTheme {
        ZPlayApp()
    }
}
