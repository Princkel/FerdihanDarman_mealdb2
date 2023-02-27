package com.bootcamp.ferdihandarman_mealdb.data

import android.util.Log
import com.bootcamp.ferdihandarman_mealdb.data.network.api.MealApi
import com.bootcamp.ferdihandarman_mealdb.data.network.handler.NetworkResult
import com.bootcamp.ferdihandarman_mealdb.model.ResponseMeal
import com.bootcamp.ferdihandarman_mealdb.model.ResponseMealId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val mealApi: MealApi) {
    suspend fun getMeal(): Flow<NetworkResult<ResponseMeal>> = flow {
        try {
            emit(NetworkResult.Loading(true))
            val meal = mealApi.getMeal()

            // Request data successful
            if (meal.isSuccessful) {
                val responseBody = meal.body()

                // if data not empty / if data empty
                if (responseBody?.meals?.isNotEmpty() == true) {
                    emit(NetworkResult.Success(responseBody))
                } else {
                    emit(NetworkResult.Error("Meal List Not Found"))
                }
            } else {

                // Request data failed
                Log.d("apiServiceError", "statusCode:${meal.code()}, message:${meal.message()}")
                emit(NetworkResult.Error("Failed to fetch data from server"))
            }

        } catch (err: Exception) {
            err.printStackTrace()
            Log.d("remoteError", "${err.message}")
            emit(NetworkResult.Error("Something went wrong, Please check log."))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getMealId(mealId: String): Flow<NetworkResult<ResponseMealId>> = flow {
        try {
            emit(NetworkResult.Loading(true))
            val mealDetail = mealApi.getMealId(mealId)

            if (mealDetail.isSuccessful) {
                val responseBody = mealDetail.body()

                if (responseBody != null) {
                    emit(NetworkResult.Success(responseBody))
                } else {
                    emit(NetworkResult.Error("Can't fetch detail meal"))
                }
            } else {
                Log.d("apiServiceError", "statusCode:${mealDetail.code()}, message:${mealDetail.message()}")
                emit(NetworkResult.Error("Failed to fetch data from server"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("remoteError", "${e.message}")
            emit(NetworkResult.Error("Something went wrong, Please check log."))
        }
    }.flowOn(Dispatchers.IO)
}
