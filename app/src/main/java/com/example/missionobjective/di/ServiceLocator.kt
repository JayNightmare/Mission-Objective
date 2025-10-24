package com.example.missionobjective.di

import android.content.Context
import com.example.missionobjective.data.ObjectiveRepository
import com.example.missionobjective.data.local.AppDatabase

object ServiceLocator {
    @Volatile private var repo: ObjectiveRepository? = null

    fun repository(context: Context): ObjectiveRepository =
        repo ?: synchronized(this) {
            repo ?: ObjectiveRepository(AppDatabase.get(context).objectiveDao()).also { repo = it }
        }
}

