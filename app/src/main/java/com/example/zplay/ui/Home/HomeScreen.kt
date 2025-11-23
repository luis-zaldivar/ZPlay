package com.example.zplay.ui.Home

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaHome(modifier: Modifier = Modifier) {

    // ---------- Drawer (menú lateral) ----------
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val tabs = listOf("CANCIONES", "RECIENTE", "ÁLBUM", "ARTISTAS")
    var selectedTab by rememberSaveable { mutableStateOf(0) }

    val canciones = List(15) { index -> "Canción ${index + 1}" }

    var isPlaying by remember { mutableStateOf(true) }

    // ---------- TODA LA PANTALLA ENVUELTA EN DRAWER ----------
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenu(
                onSelect = {
                    // Cerrar menú al elegir
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Biblioteca", color = Color.White, fontSize = 20.sp) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }  // ABRE MENU LATERAL
                            }
                        ) {
                            Icon(Icons.Default.Menu, null, tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Cast, null, tint = Color.White)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Search, null, tint = Color.White)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.MoreVert, null, tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF3F51B5)
                    )
                )
            },
            bottomBar = {
                MiniPlayer(
                    onOpenPlayer = { },
                    onPlayPause = { isPlaying = !isPlaying },
                    isPlaying = isPlaying
                )
            }
        ) { padding ->

            Column(
                modifier = modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {

                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color(0xFF3F51B5),
                    contentColor = Color.White,
                    edgePadding = 0.dp
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    title,
                                    color =
                                        if (selectedTab == index)
                                            Color.White
                                        else
                                            Color.White.copy(0.7f)
                                )
                            }
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF2B201C))
                ) {
                    items(canciones) { nombre ->
                        SongRow(nombre)
                    }
                }
            }
        }
    }
}

@Composable
fun SongRow(nombre: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            Icons.Default.MusicNote,
            contentDescription = null,
            tint = Color(0xFFE8D5CB),
            modifier = Modifier.size(32.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(nombre, fontSize = 16.sp, color = Color.White)
            Text("Artista", fontSize = 13.sp, color = Color.Gray)
        }
    }
}

@Composable
fun MiniPlayer(
    onOpenPlayer: () -> Unit,
    onPlayPause: () -> Unit,
    isPlaying: Boolean
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .navigationBarsPadding()
            .background(Color(0xFF3F51B5))
            .clickable { onOpenPlayer() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(55.dp)
                .background(Color.DarkGray, shape = RoundedCornerShape(6.dp))
        )

        Spacer(Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text("Shirogane", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text("LiSA", color = Color.White.copy(0.7f), fontSize = 12.sp)
        }

        IconButton(
            onClick = { onPlayPause() }
        ) {
            Icon(
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun DrawerMenu(onSelect: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(250.dp)
            .background(Color(0xFFF0F0F0))
            .padding(16.dp)
    ) {
        Text("Menú", fontSize = 22.sp)

        Spacer(Modifier.height(20.dp))

        DrawerItem("Inicio", onSelect)
        DrawerItem("Playlist", onSelect)
        DrawerItem("Favoritos", onSelect)
        DrawerItem("Ajustes", onSelect)
        DrawerItem("Salir", onSelect)
    }
}

@Composable
fun DrawerItem(text: String, onSelect: (String) -> Unit) {
    Text(
        text,
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(text) }
            .padding(vertical = 12.dp)
    )
}
