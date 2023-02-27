package com.bootcamp.ferdihandarman_mealdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bootcamp.ferdihandarman_mealdb.data.RemoteDataSource
import com.bootcamp.ferdihandarman_mealdb.data.Repository
import com.bootcamp.ferdihandarman_mealdb.data.network.Service
import com.bootcamp.ferdihandarman_mealdb.data.network.handler.NetworkResult
import com.bootcamp.ferdihandarman_mealdb.model.ResponseMeal
import kotlinx.coroutines.launch


class MainViewModel() : ViewModel() {

    private val remoteService = Service.MealService
    private val remote = RemoteDataSource(remoteService)
    private val repository = Repository(remote)

    private var _recipeList: MutableLiveData<NetworkResult<ResponseMeal>> = MutableLiveData()
    val recipeList: LiveData<NetworkResult<ResponseMeal>> = _recipeList

    init {
        fetchRecipeList()
    }

    private fun fetchRecipeList() {
        viewModelScope.launch {
            repository.remote?.getMeal()?.collect { res ->
                _recipeList.value = res
            }
        }
    }

}