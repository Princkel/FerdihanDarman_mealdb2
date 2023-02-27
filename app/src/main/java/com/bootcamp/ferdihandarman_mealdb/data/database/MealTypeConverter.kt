package com.bootcamp.ferdihandarman_mealdb.data.database

import androidx.room.TypeConverter
import com.bootcamp.ferdihandarman_mealdb.model.ResponseMeal
import com.bootcamp.ferdihandarman_mealdb.model.ResponseMealId
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun mealDataToString(meal: ResponseMealId?): String {
        return gson.toJson(meal)
    }

    @TypeConverter
    fun mealStringToData(string: String): ResponseMealId? {
        val listType = object : TypeToken<ResponseMealId?>() {}.type
        return gson.fromJson(string, listType)
    }
}