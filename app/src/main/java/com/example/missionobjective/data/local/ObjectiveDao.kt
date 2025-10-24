package com.example.missionobjective.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ObjectiveDao {
    @Query("SELECT * FROM objectives ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<ObjectiveEntity>>

    @Query("SELECT * FROM objectives WHERE id = :id")
    fun observeById(id: Long): Flow<ObjectiveEntity?>

    @Query("SELECT * FROM objectives WHERE id = :id")
    suspend fun getById(id: Long): ObjectiveEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ObjectiveEntity): Long

    @Update
    suspend fun update(entity: ObjectiveEntity)

    @Query("DELETE FROM objectives WHERE id = :id")
    suspend fun deleteById(id: Long)
}
