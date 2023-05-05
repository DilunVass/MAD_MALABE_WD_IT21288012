package com.example.foodshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodshop.data.Favourites
import com.example.foodshop.data.Review
import com.example.foodshop.databinding.FavProductItemBinding
import com.example.foodshop.databinding.ReviewitemBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.RevProductViewholder>(){

    inner class RevProductViewholder(private val binding: ReviewitemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(review: Review){
            binding.apply {

                tvreview.text = review.review

            }

        }
    }

    private val diffCallback = object  : DiffUtil.ItemCallback<Review>(){
        override fun areItemsTheSame(oldItem: Review, newItem:Review): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return  oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RevProductViewholder {
        return RevProductViewholder(
            ReviewitemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: RevProductViewholder, position: Int) {
        val review = differ.currentList[position]
        holder.bind(review)

        holder.itemView.setOnClickListener{
            onProductclick?.invoke(review)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onProductclick:((Review) -> Unit)? = null
}