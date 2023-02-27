package com.bootcamp.ferdihandarman_mealdb.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.bootcamp.ferdihandarman_mealdb.data.LocalDataSource
import com.bootcamp.ferdihandarman_mealdb.data.RemoteDataSource
import com.bootcamp.ferdihandarman_mealdb.data.Repository
import com.bootcamp.ferdihandarman_mealdb.data.database.MealDatabase
import com.bootcamp.ferdihandarman_mealdb.data.database.MealEntity
import com.bootcamp.ferdihandarman_mealdb.data.network.Service
import com.bootcamp.ferdihandarman_mealdb.data.network.handler.NetworkResult
import com.bootcamp.ferdihandarman_mealdb.model.ResponseMealId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): AndroidViewModel(application) {

    // API
    private val mealService = Service.MealService
    private val remote = RemoteDataSource(mealService)

    // LOCAL
    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataSource(mealDao)

    private val repository = Repository(remote)

    private var _mealDetail: MutableLiveData<NetworkResult<ResponseMealId>> = MutableLiveData()
    val recipeDetail: LiveData<NetworkResult<ResponseMealId>> = _mealDetail

    fun fetchMealDetail(idMeal: String) {
        viewModelScope.launch {
            repository.remote!!.getMealId(idMeal).collect { result ->
                _mealDetail.value = result
            }
        }
    }

    val favoriteMealList:LiveData<List<MealEntity>> = repository.local!!.listMeal().asLiveData()

    fun insertFavoriteMeal(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.insertMeal(mealEntity)
        }
    }

    fun deleteFavoriteMeal(mealEntity: MealEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.deleteMeal(mealEntity)
        }
    }
}