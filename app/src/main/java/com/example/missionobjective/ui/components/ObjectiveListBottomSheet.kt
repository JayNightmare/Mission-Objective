package com.example.missionobjective.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.missionobjective.data.Objective

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjectiveListBottomSheet(
    objectives: List<Objective>,
    onDismiss: () -> Unit,
    onToggleComplete: (Long) -> Unit,
    onObjectiveClick: (Long) -> Unit
) {
    /*

    TODO :
    1. Add search bar or chips functionality to filter objectives
    2. Improve UI/UX with better styling and animations
    3. Slice details with "..." if too long
    4. Consider adding priority indicators or categories for objectives

    */

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = "Objectives",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentWidth()
            )
            Spacer(Modifier.height(8.dp))
            LazyColumn {
                items(objectives, key = { it.id }) { objective ->
                    ListItem(
                        headlineContent = { Text(objective.title) },
                        supportingContent = { Text(objective.description) },
                        leadingContent = {
                            Checkbox(
                                checked = objective.isCompleted,
                                onCheckedChange = { onToggleComplete(objective.id) }
                            )
                        },
                        trailingContent = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onObjectiveClick(objective.id) }
                            .alpha(if (objective.isCompleted) 0.5f else 1f)
                        )
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
