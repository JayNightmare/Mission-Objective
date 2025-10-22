// filepath: d:\Documents\Mission Objective\app\src\main\java\com\example\missionobjective\ui\screens\ObjectiveFormScreen.kt
package com.example.missionobjective.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.missionobjective.data.Objective
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjectiveFormScreen(
    title: String,
    initial: Objective?,
    onCancel: () -> Unit,
    onSave: (title: String, description: String, latLng: LatLng, isCompleted: Boolean) -> Unit
) {
    var titleText by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }
    var latitudeText by remember { mutableStateOf("") }
    var longitudeText by remember { mutableStateOf("") }
    var isCompleted by remember { mutableStateOf(false) }

    LaunchedEffect(initial) {
        if (initial != null) {
            titleText = initial.title
            descriptionText = initial.description
            latitudeText = initial.location.latitude.toString()
            longitudeText = initial.location.longitude.toString()
            isCompleted = initial.isCompleted
        }
    }

    val (isLatValid, isLngValid) = remember(latitudeText, longitudeText) {
        val lat = latitudeText.toDoubleOrNull()
        val lng = longitudeText.toDoubleOrNull()
        val latOk = lat != null && lat in -90.0..90.0
        val lngOk = lng != null && lng in -180.0..180.0
        mutableStateOf(latOk to lngOk)
    }.value

    val canSave = titleText.isNotBlank() && descriptionText.isNotBlank() && isLatValid && isLngValid

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val lat = latitudeText.toDoubleOrNull()
                        val lng = longitudeText.toDoubleOrNull()
                        if (lat != null && lng != null) {
                            onSave(titleText.trim(), descriptionText.trim(), LatLng(lat, lng), isCompleted)
                        }
                    }, enabled = canSave) {
                        Icon(Icons.Default.Save, contentDescription = "Save")
                    }
                }
            )
        }
    ) { padding ->
        Content(
            padding = padding,
            titleText = titleText,
            onTitleChange = { titleText = it },
            descriptionText = descriptionText,
            onDescriptionChange = { descriptionText = it },
            latitudeText = latitudeText,
            onLatitudeChange = { latitudeText = it },
            longitudeText = longitudeText,
            onLongitudeChange = { longitudeText = it },
            isLatValid = isLatValid,
            isLngValid = isLngValid,
            isCompleted = isCompleted,
            onCompletedChange = { isCompleted = it },
            onCancel = onCancel,
            onSave = {
                val lat = latitudeText.toDoubleOrNull()
                val lng = longitudeText.toDoubleOrNull()
                if (lat != null && lng != null) {
                    onSave(titleText.trim(), descriptionText.trim(), LatLng(lat, lng), isCompleted)
                }
            },
            canSave = canSave
        )
    }
}

@Composable
private fun Content(
    padding: PaddingValues,
    titleText: String,
    onTitleChange: (String) -> Unit,
    descriptionText: String,
    onDescriptionChange: (String) -> Unit,
    latitudeText: String,
    onLatitudeChange: (String) -> Unit,
    longitudeText: String,
    onLongitudeChange: (String) -> Unit,
    isLatValid: Boolean,
    isLngValid: Boolean,
    isCompleted: Boolean,
    onCompletedChange: (Boolean) -> Unit,
    onCancel: () -> Unit,
    onSave: () -> Unit,
    canSave: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = titleText,
            onValueChange = onTitleChange,
            label = { Text("Title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = descriptionText,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = latitudeText,
            onValueChange = onLatitudeChange,
            label = { Text("Latitude (-90 to 90)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = !isLatValid,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = longitudeText,
            onValueChange = onLongitudeChange,
            label = { Text("Longitude (-180 to 180)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = !isLngValid,
            modifier = Modifier.fillMaxWidth()
        )
        androidx.compose.foundation.layout.Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(checked = isCompleted, onCheckedChange = onCompletedChange)
            Text("Completed")
        }
        Spacer(Modifier.height(8.dp))
        androidx.compose.foundation.layout.Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(onClick = onCancel, modifier = Modifier.weight(1f)) { Text("Cancel") }
            TextButton(onClick = onSave, enabled = canSave, modifier = Modifier.weight(1f)) { Text("Save") }
        }
    }
}
