package com.example.missionobjective.data

import com.example.missionobjective.data.local.ObjectiveDao
import com.example.missionobjective.data.local.ObjectiveEntity
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObjectiveRepository(private val dao: ObjectiveDao) {

    val objectives: Flow<List<Objective>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    fun objectiveById(id: Long): Flow<Objective?> =
        dao.observeById(id).map { it?.toDomain() }

    suspend fun create(title: String, description: String, location: LatLng, isCompleted: Boolean): Long {
        val entity = ObjectiveEntity(
            title = title,
            description = description,
            lat = location.latitude,
            lng = location.longitude,
            isCompleted = isCompleted
        )
        return dao.insert(entity)
    }

    suspend fun update(obj: Objective) {
        val entity = ObjectiveEntity(
            id = obj.id,
            title = obj.title,
            description = obj.description,
            lat = obj.location.latitude,
            lng = obj.location.longitude,
            isCompleted = obj.isCompleted,
            updatedAt = System.currentTimeMillis()
        )
        dao.update(entity)
    }

    suspend fun toggleCompleted(id: Long) {
        val entity = dao.getById(id) ?: return
        dao.update(entity.copy(isCompleted = !entity.isCompleted, updatedAt = System.currentTimeMillis()))
    }

    suspend fun delete(id: Long) {
        dao.deleteById(id)
    }
}

private fun ObjectiveEntity.toDomain(): Objective = Objective(
    id = id,
    title = title,
    description = description,
    location = LatLng(lat, lng),
    isCompleted = isCompleted
)

