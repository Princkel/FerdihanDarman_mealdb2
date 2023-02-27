package com.bootcamp.ferdihandarman_mealdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bootcamp.ferdihandarman_mealdb.data.LocalDataSource
import com.bootcamp.ferdihandarman_mealdb.data.RemoteDataSource
import com.bootcamp.ferdihandarman_mealdb.data.Repository
import com.bootcamp.ferdihandarman_mealdb.data.database.MealDatabase
import com.bootcamp.ferdihandarman_mealdb.data.database.MealEntity
import com.bootcamp.ferdihandarman_mealdb.data.network.Service
import com.bootcamp.ferdihandarman_mealdb.data.network.handler.NetworkResult
import com.bootcamp.ferdihandarman_mealdb.model.ResponseMeal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteDetailViewModel(application: Application): AndroidViewModel(application) {
    // Api
    private val remoteService = Service.MealService
    private val remote = RemoteDataSource(remoteService)

    // LOCAL
    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataSource(mealDao)

    private val repository = Repository(remote,local)

    private var _recommendationList: MutableLiveData<NetworkResult<ResponseMeal>> = MutableLiveData()
    val recommendationList: LiveData<NetworkResult<ResponseMeal>> = _recommendationList


    fun fetchListMeal() {
        viewModelScope.launch {
            repository.remote!!.getMeal().collect { res ->
                _recommendationList.value = res
            }
        }
    }

    fun deleteFavoriteMeal(mealEntity: MealEntity?){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.deleteMeal(mealEntity)
        }
    }
}