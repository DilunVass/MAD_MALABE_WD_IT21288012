package com.example.foodshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodshop.data.Favourites
import com.example.foodshop.databinding.FavProductItemBinding

class favproductsadapter : RecyclerView.Adapter<favproductsadapter.FavProductViewholder>() {
    inner class FavProductViewholder(private val binding: FavProductItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(favourites: Favourites){
            binding.apply {
                Glide.with(itemView).load(favourites.product.images[0]).into(favProduct)
                tvProductCartName.text = favourites.product.name

            }

        }
    }

    private val diffCallback = object  : DiffUtil.ItemCallback<Favourites>(){
        override fun areItemsTheSame(oldItem: Favourites, newItem: Favourites): Boolean {
            return oldItem.product.id == newItem.product.id
        }

        override fun areContentsTheSame(oldItem: Favourites, newItem:Favourites): Boolean {
            return  oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavProductViewholder {
        return FavProductViewholder(
            FavProductItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: FavProductViewholder, position: Int) {
        val favProducts = differ.currentList[position]
        holder.bind(favProducts)

        holder.itemView.setOnClickListener{
            onProductclick?.invoke(favProducts)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onProductclick:((Favourites) -> Unit)? = null
}