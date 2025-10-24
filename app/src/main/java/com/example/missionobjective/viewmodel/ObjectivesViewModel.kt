package com.example.missionobjective.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.missionobjective.data.Objective
import com.example.missionobjective.data.ObjectiveRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ObjectivesViewModel(
    private val repo: ObjectiveRepository
) : ViewModel() {

    data class State(
        val objectives: List<Objective> = emptyList()
    )

    var state by mutableStateOf(State())
        private set

    init {
        // Collect changes from repository and update compose state
        viewModelScope.launch {
            repo.objectives.collectLatest { list ->
                state = State(objectives = list)
            }
        }
    }

    fun toggleCompleted(id: Long) {
        viewModelScope.launch { repo.toggleCompleted(id) }
    }

    // Create a new objective and return its generated ID
    suspend fun createObjective(
        title: String,
        description: String,
        location: LatLng,
        isCompleted: Boolean = false
    ): Long {
        return repo.create(title, description, location, isCompleted)
    }

    // Update an existing objective by ID; no-op if not found
    fun updateObjective(
        id: Long,
        title: String,
        description: String,
        location: LatLng,
        isCompleted: Boolean
    ) {
        viewModelScope.launch {
            repo.update(
                Objective(
                    id = id,
                    title = title,
                    description = description,
                    location = location,
                    isCompleted = isCompleted
                )
            )
        }
    }

    // Delete an objective by ID; no-op if not found
    fun deleteObjective(id: Long) {
        viewModelScope.launch { repo.delete(id) }
    }

    class Factory(private val repo: ObjectiveRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(ObjectivesViewModel::class.java))
            return ObjectivesViewModel(repo) as T
        }
    }
}
