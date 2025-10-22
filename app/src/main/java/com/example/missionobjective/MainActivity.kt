package com.example.missionobjective

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.missionobjective.ui.screens.MapScreen
import com.example.missionobjective.ui.screens.ObjectiveDetailsScreen
import com.example.missionobjective.ui.screens.ObjectiveFormScreen
import com.example.missionobjective.ui.theme.MissionObjectiveTheme
import com.example.missionobjective.viewmodel.ObjectivesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MissionObjectiveApp()
        }
    }
}

@Composable
fun MissionObjectiveApp() {
    MissionObjectiveTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            val viewModel: ObjectivesViewModel = viewModel()

            NavHost(navController = navController, startDestination = "map") {
                composable("map") {
                    MapScreen(
                        objectivesState = viewModel.state,
                        onToggleComplete = { id -> viewModel.toggleCompleted(id) },
                        onObjectiveClick = { id ->
                            navController.navigate("details/$id")
                        },
                        onAdd = { // Navigate to create form
                            navController.navigate("create")
                        }
                    )
                }
                composable(
                    route = "details/{objectiveId}",
                    arguments = listOf(navArgument("objectiveId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getLong("objectiveId") ?: -1L
                    val objective = remember(viewModel.state.objectives) {
                        viewModel.state.objectives.find { it.id == id }
                    }
                    ObjectiveDetailsScreen(
                        objective = objective,
                        onBack = { navController.popBackStack() },
                        onToggleComplete = { viewModel.toggleCompleted(id) },
                        onEdit = { navController.navigate("edit/$id") },
                        onDelete = {
                            viewModel.deleteObjective(id)
                            // After deletion, return to map
                            navController.popBackStack(route = "map", inclusive = false)
                        }
                    )
                }
                composable(
                    route = "create"
                ) {
                    ObjectiveFormScreen(
                        title = "New Objective",
                        initial = null,
                        onCancel = { navController.popBackStack() },
                        onSave = { title, description, latLng, isCompleted ->
                            val newId = viewModel.createObjective(title, description, latLng, isCompleted)
                            // Navigate to details of the created objective
                            navController.popBackStack()
                            navController.navigate("details/$newId")
                        }
                    )
                }
                composable(
                    route = "edit/{objectiveId}",
                    arguments = listOf(navArgument("objectiveId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getLong("objectiveId") ?: -1L
                    val objective = remember(viewModel.state.objectives) {
                        viewModel.state.objectives.find { it.id == id }
                    }
                    ObjectiveFormScreen(
                        title = "Edit Objective",
                        initial = objective,
                        onCancel = { navController.popBackStack() },
                        onSave = { title, description, latLng, isCompleted ->
                            viewModel.updateObjective(id, title, description, latLng, isCompleted)
                            // Return to previous screen (details)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
