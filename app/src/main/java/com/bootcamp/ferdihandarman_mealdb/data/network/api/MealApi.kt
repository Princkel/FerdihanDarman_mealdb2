package com.bootcamp.ferdihandarman_mealdb.data.network.api

import com.bootcamp.ferdihandarman_mealdb.model.ResponseMeal
import com.bootcamp.ferdihandarman_mealdb.model.ResponseMealId
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("filter.php?c=Seafood")
    suspend fun getMeal(): Response<ResponseMeal>

    @GET("lookup.php")
    suspend fun getMealId(
        @Query("i") i:String
    ): Response<ResponseMealId>

}