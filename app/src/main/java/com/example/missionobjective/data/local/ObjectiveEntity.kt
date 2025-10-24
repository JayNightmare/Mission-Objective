package com.example.missionobjective.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.vo.PrimaryKey

@Entity(tableName = "objectives")
data class ObjectiveEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val description: String,
    val lat: Double,
    val lng: Double,
    val isCompleted: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)

