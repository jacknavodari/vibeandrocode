package com.example.ide.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ide.data.repository.AIRepository
import com.example.ide.data.repository.FileRepository
import com.example.ide.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val viewModel: MainViewModel = viewModel(
        factory = com.example.ide.di.ViewModelFactory(context)
    )
    
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Projects", "Editor", "AI Chat", "Settings")
    
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Mobile IDE") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
        
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) },
                    icon = {
                        when (index) {
                            0 -> Icon(Icons.Default.Folder, contentDescription = null)
                            1 -> Icon(Icons.Default.Edit, contentDescription = null)
                            2 -> Icon(Icons.Default.Chat, contentDescription = null)
                            3 -> Icon(Icons.Default.Settings, contentDescription = null)
                        }
                    }
                )
            }
        }
        
        when (selectedTab) {
            0 -> ProjectsScreen(viewModel)
            1 -> EditorScreen(viewModel)
            2 -> ChatScreen(viewModel)
            3 -> SettingsScreen(viewModel)
        }
    }
}