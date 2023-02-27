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
import com.bootcamp.ferdihandarman_mealdb.adapter.FavoriteAdapter
import com.bootcamp.ferdihandarman_mealdb.data.database.MealEntity
import com.bootcamp.ferdihandarman_mealdb.databinding.ActivityFavoriteBinding
import com.bootcamp.ferdihandarman_mealdb.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private val favoriteAdapter by lazy { FavoriteAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteViewModel.favoriteMealList.observe(this) { res ->
            if (res.isEmpty()) {
                binding.apply {
                    rvFavoriteList.isVisible = false
                    errorText.isVisible = true
                }
            } else {
                Log.d("test", res.toString())
                binding.rvFavoriteList.apply {
                    adapter = favoriteAdapter
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(
                        this@FavoriteActivity
                    )
                }

                favoriteAdapter.apply {
                    setData(res)
                    setOnItemClickCallback(object:FavoriteAdapter.IOnFavoriteItemCallBack{
                        override fun onFavoriteItemClickCallback(data: MealEntity) {
                            val detailFavorite = Intent(this@FavoriteActivity, FavoriteDetailActivity::class.java)
                            detailFavorite.putExtra(FavoriteDetailActivity.EXTRA_FAVORITE_MEAL,data)
                            startActivity(detailFavorite)
                        }
                    })
                }
            }

            val colorStateList = ContextCompat.getColorStateList(this, R.color.bottom_nav_item_color)

            binding.apply {
                bottomNavigationView.menu.getItem(1).setChecked(true)
                bottomNavigationView.itemTextColor = colorStateList
                bottomNavigationView.itemIconTintList = colorStateList
                bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.MainActivity -> {
                            val intent = Intent(this@FavoriteActivity, MainActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(0, 0)
                            true
                        }
                        R.id.FavoriteActivity -> {
                            true
                        }
                        else -> {
                            true
                        }
                    }

                }
            }
        }
    }
}