package com.example.missionobjective.ui.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import com.example.missionobjective.BuildConfig
import com.example.missionobjective.ui.components.ObjectiveListBottomSheet
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    objectivesState: com.example.missionobjective.viewmodel.ObjectivesViewModel.State,
    onToggleComplete: (Long) -> Unit,
    onObjectiveClick: (Long) -> Unit,
    onAdd: () -> Unit,
    showSheet: Boolean = false
) {
    /*

    TODO :
    1. Center map based on all objectives
    2. Show sheet on open, but remember state when navigating back to map
    3. Clicking on the marker opens a card with details
    4. Go to user's current location on map if permission granted
    5. Improve UI/UX with better styling and animations
    6. Consider clustering markers if there are many objectives close together
    7. When all objectives are completed, show a congratulatory message or animation (possibly confetti and sound)

     */


//    TODO : Center map based on all objectives
    val center = objectivesState.objectives.firstOrNull()?.location ?: LatLng(0.0, 0.0)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(center, 13f)
    }

//    TODO : Show sheet on open, but remember state when navigating back to map
    val sheetVisible = remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mission Objective") },
                actions = {
                    IconButton(onClick = { sheetVisible.value = true }) {
                        Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Open objectives")
                    }
                    IconButton(onClick = onAdd) {
                        Icon(Icons.Default.Add, contentDescription = "Add objective")
                    }
                }
            )
        },
        floatingActionButton = {
            MyLocationFab()
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = com.google.maps.android.compose.MapUiSettings(
                    compassEnabled = true,
                    zoomControlsEnabled = false
                )
            ) {
//              TODO : Clicking on the marker opens a card with details
                objectivesState.objectives.forEach { objective ->
                    Marker(
                        state = MarkerState(position = objective.location),
                        title = objective.title,
                        snippet = objective.description,
                        onClick = {
                            onObjectiveClick(objective.id)
                            true
                        }
                    )
                }
            }

            if (sheetVisible.value) {
                ObjectiveListBottomSheet(
                    objectives = objectivesState.objectives,
                    onDismiss = { sheetVisible.value = false },
                    onToggleComplete = onToggleComplete,
                    onObjectiveClick = onObjectiveClick
                )
            }

            if (BuildConfig.MAPS_API_KEY.isEmpty()) {
                Surface(
                    color = Color(0xFFFFE082),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Missing MAPS_API_KEY. Set it in local.properties.",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun MyLocationFab() {
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
//            TODO : Go to user's current location on map if permission granted
        }
    )
    FloatingActionButton(onClick = {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }) {
        Icon(Icons.Default.MyLocation, contentDescription = "My Location")
    }
}
