package com.example.missionobjective.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.missionobjective.data.Objective
import com.google.android.gms.maps.model.LatLng

class ObjectivesViewModel : ViewModel() {

    data class State(
        val objectives: List<Objective> = emptyList()
    )

    var state by mutableStateOf(
        State(
            objectives = listOf(
                Objective(
                    id = 1L,
                    title = "Secure the Drop Point",
                    description = "Meet the contact at the plaza fountain and retrieve the package.",
                    location = LatLng(19.432608, -99.133209) // Mexico City center
                ),
                Objective(
                    id = 2L,
                    title = "Recon the North Sector",
                    description = "Survey the blocks north of the station and mark entry routes.",
                    location = LatLng(19.436, -99.13)
                ),
                Objective(
                    id = 3L,
                    title = "Acquire Transport",
                    description = "Locate a vehicle suitable for extraction.",
                    location = LatLng(19.428, -99.14)
                )
            )
        )
    )
        private set

    fun toggleCompleted(id: Long) {
        state = state.copy(
            objectives = state.objectives.map { obj ->
                if (obj.id == id) obj.copy(isCompleted = !obj.isCompleted) else obj
            }
        )
    }

    // Generate the next unique ID based on current objectives
    private fun nextId(): Long {
        val max = state.objectives.maxOfOrNull { it.id } ?: 0L
        return max + 1
    }

    // Create a new objective and return its generated ID
    fun createObjective(
        title: String,
        description: String,
        location: LatLng,
        isCompleted: Boolean = false
    ): Long {
        val newId = nextId()
        val newObj = Objective(
            id = newId,
            title = title,
            description = description,
            location = location,
            isCompleted = isCompleted
        )
        state = state.copy(objectives = state.objectives + newObj)
        return newId
    }

    // Update an existing objective by ID; no-op if not found
    fun updateObjective(
        id: Long,
        title: String,
        description: String,
        location: LatLng,
        isCompleted: Boolean
    ) {
        state = state.copy(
            objectives = state.objectives.map { obj ->
                if (obj.id == id) obj.copy(
                    title = title,
                    description = description,
                    location = location,
                    isCompleted = isCompleted
                ) else obj
            }
        )
    }

    // Delete an objective by ID; no-op if not found
    fun deleteObjective(id: Long) {
        state = state.copy(
            objectives = state.objectives.filterNot { it.id == id }
        )
    }
}
