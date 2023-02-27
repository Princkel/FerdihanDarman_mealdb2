package com.bootcamp.ferdihandarman_mealdb.data.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealEntity: MealEntity)

    @Query("SELECT * FROM seafood_meal ORDER BY id ASC")
    fun listMeal(): Flow<List<MealEntity>>

    @Delete()
    suspend fun deleteMeal(mealEntity: MealEntity?)

    @Query("DELETE FROM seafood_meal")
    suspend fun deleteAllMeal()
}