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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.zplay.ui.navigation.ZPlayApp          // ← IMPORTA TU NAVEGACIÓN
import com.example.zplay.ui.theme.ZPlayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ZPlayTheme {
                AppConPermisos()
            }
        }
    }
}

/* -----------------------------------------------------------
                CONTROLADOR DE PERMISOS
----------------------------------------------------------- */

@Composable
fun AppConPermisos() {

    val context = LocalContext.current
    var permisoConcedido by remember { mutableStateOf(false) }

    val permiso =
        if (Build.VERSION.SDK_INT >= 33)
            android.Manifest.permission.READ_MEDIA_AUDIO
        else
            android.Manifest.permission.READ_EXTERNAL_STORAGE

    val estado = ContextCompat.checkSelfPermission(context, permiso)

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        permisoConcedido = concedido
    }

    LaunchedEffect(Unit) {
        if (estado == PackageManager.PERMISSION_GRANTED) {
            permisoConcedido = true
        } else launcher.launch(permiso)
    }

    if (permisoConcedido) {
        ZPlayApp()   // ← TU NAVEGACIÓN VIVE FUERA DEL MAIN
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Necesitamos permiso para acceder a tu música 🎵")
        }
    }
}
