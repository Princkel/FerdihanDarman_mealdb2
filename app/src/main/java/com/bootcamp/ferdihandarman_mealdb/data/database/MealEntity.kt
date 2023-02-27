package com.bootcamp.ferdihandarman_mealdb.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomMasterTable.TABLE_NAME
import com.bootcamp.ferdihandarman_mealdb.model.MealsItemId
import com.bootcamp.ferdihandarman_mealdb.model.ResponseMeal
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val meal: MealsItemId?
): Parcelable