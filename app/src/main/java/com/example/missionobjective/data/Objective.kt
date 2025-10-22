package com.example.missionobjective.data

import com.google.android.gms.maps.model.LatLng

data class Objective(
    val id: Long,
    val title: String,
    val description: String,
    val location: LatLng,
    val isCompleted: Boolean = false
)
