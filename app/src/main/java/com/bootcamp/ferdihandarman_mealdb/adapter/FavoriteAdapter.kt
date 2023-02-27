package com.bootcamp.ferdihandarman_mealdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.ferdihandarman_mealdb.R
import com.bootcamp.ferdihandarman_mealdb.data.database.MealEntity
import com.bootcamp.ferdihandarman_mealdb.databinding.FavoriteMealRowLayoutBinding
import com.bumptech.glide.Glide

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<MealEntity>() {
        override fun areItemsTheSame(oldItem: MealEntity, newItem: MealEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MealEntity, newItem: MealEntity): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    private lateinit var onFavoriteItemCallBack: IOnFavoriteItemCallBack

    inner class FavoriteViewHolder(private val binding: FavoriteMealRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MealEntity) {
            binding.apply {
//                val result = item.meal.meals
//                for (dataMeal in result!!) {
//                    Glide.with(itemView.context)
//                        .load(dataMeal!!.strMealThumb)
//                        .error(R.drawable.img_placeholder)
//                        .into(ivPoster)
//                    tvMeal.text = dataMeal!!.strMeal
//                }
                Glide.with(itemView.context)
                    .load(item.meal?.strMealThumb)
                    .error(R.drawable.ic_launcher_background)
                    .into(ivFavorite)
                tvFavoriteTitle.text = item.meal?.strMeal
                itemView.setOnClickListener {
                    onFavoriteItemCallBack.onFavoriteItemClickCallback(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rowBinding = FavoriteMealRowLayoutBinding.inflate(layoutInflater, parent, false)
        return FavoriteViewHolder(rowBinding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.FavoriteViewHolder, position: Int) {
        val itemData = differ.currentList[position]
        holder.bind(itemData)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(list: List<MealEntity?>?) {
        differ.submitList(list)
    }

    fun setOnItemClickCallback(action: IOnFavoriteItemCallBack) {
        this.onFavoriteItemCallBack = action
    }

    interface IOnFavoriteItemCallBack {
        fun onFavoriteItemClickCallback(data: MealEntity)
    }
}
