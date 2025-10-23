package com.example.missionobjective.domain.model

enum class QuestType { EAT, DRINK, WALK, CALENDAR, CUSTOM }
enum class QuestStatus { TODO, ACTIVE, DONE, SNOOZED, EXPIRED }

data class Quest(
    val id: Long = 0L,
    val title: String,
    val type: QuestType,
    val status: QuestStatus = QuestStatus.TODO,
    val xpReward: Int = 10,
    val dueAt: Long? = null,
    val locationLat: Double? = null,
    val locationLng: Double? = null,
    val radiusM: Float? = null,
    val source: String? = null,
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

data class UserStats(
    val id: Int = 1,
    val level: Int = 1,
    val xp: Int = 0,
    val streakCurrent: Int = 0,
    val streakBest: Int = 0,
    val lastCompletionDay: Long? = null
)
