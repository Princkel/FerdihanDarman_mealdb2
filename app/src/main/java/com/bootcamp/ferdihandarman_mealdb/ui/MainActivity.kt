package com.bootcamp.ferdihandarman_mealdb.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bootcamp.ferdihandarman_mealdb.R
import com.bootcamp.ferdihandarman_mealdb.adapter.MealAdapter
import com.bootcamp.ferdihandarman_mealdb.data.network.handler.NetworkResult
import com.bootcamp.ferdihandarman_mealdb.databinding.ActivityMainBinding
import com.bootcamp.ferdihandarman_mealdb.model.MealsItem
import com.bootcamp.ferdihandarman_mealdb.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private val recipeAdapter by lazy {
        MealAdapter()
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.recipeList.observe(this@MainActivity) { res ->
            when (res) {
                is NetworkResult.Loading -> {
                    handleUi(
                        recyclerView = false,
                        progressbar = true,
                        errorTv = false,
                    )
                }
                is NetworkResult.Error -> {
                    binding.errorText.text = res.errorMessage
                    handleUi(
                        recyclerView = false,
                        progressbar = false,
                        errorTv = true,
                    )
                }
                is NetworkResult.Success -> {
                    val recipeAdapter = MealAdapter()
                    Log.d("SUCCESS", "Success retrieved data")
                    recipeAdapter.setData(res.data?.meals as List<MealsItem>)

                    binding.rvRecipesList.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        setHasFixedSize(true)
                        adapter = recipeAdapter
                    }

                    recipeAdapter.setOnItemClickCallback(object : MealAdapter.IOnItemCallBack {
                        override fun onItemClickCallback(data: MealsItem) {
                            val intent = Intent(this@MainActivity, DetailActivity::class.java)
                            intent.putExtra(DetailActivity.EXTRA_RECIPE, data)
                            startActivity(intent)
                        }
                    })

                    handleUi(
                        recyclerView = true,
                        progressbar = false,
                        errorTv = false,
                    )
                }
            }
        }

        val colorStateList = ContextCompat.getColorStateList(this, R.color.bottom_nav_item_color)
        binding.apply {
            bottomNavigationView.itemTextColor = colorStateList
            bottomNavigationView.itemIconTintList = colorStateList
            bottomNavigationView.setOnNavigationItemSelectedListener {item ->
                when(item.itemId) {
                    R.id.MainActivity -> {
                        true
                    }
                    R.id.FavoriteActivity -> {
                        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        true
                    }
                    else -> {true}
                }

            }
        }


    }

    private fun handleUi(
        recyclerView: Boolean,
        progressbar: Boolean,
        errorTv: Boolean
    ) {
        binding.apply {
            rvRecipesList.isVisible = recyclerView
            progressBar.isVisible = progressbar
            errorText.isVisible = errorTv
        }
    }
}