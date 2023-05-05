package com.example.foodshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.foodshop.data.Product
import com.example.foodshop.databinding.ViewpagerImageItemBinding

class viewPagerimages :RecyclerView.Adapter<viewPagerimages.viewPagerimagesViewHolder>(){
    inner class viewPagerimagesViewHolder(val binding :ViewpagerImageItemBinding ) : ViewHolder(binding.root){
        fun bind(imagePath:String){
            Glide.with(itemView).load(imagePath).into(binding.imageProduct)
        }
    }

    private val diffcallback = object  : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
             return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this , diffcallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewPagerimagesViewHolder {
         return viewPagerimagesViewHolder(
             ViewpagerImageItemBinding.inflate(
                 LayoutInflater.from(parent.context) , parent , false
             )
         )
    }

    override fun onBindViewHolder(holder: viewPagerimagesViewHolder, position: Int) {
        val image = differ.currentList[position]
        holder.bind(image)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick:((Product) -> Unit)? = null
}