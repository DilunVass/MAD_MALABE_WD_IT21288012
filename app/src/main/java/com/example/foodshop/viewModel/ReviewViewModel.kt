package com.example.foodshop.viewModel

import androidx.lifecycle.ViewModel
import com.example.foodshop.data.Review
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.koin.core.inject

class ReviewViewModel : ViewModel() ,  KoinComponent {
    private val firestore : FirebaseFirestore by inject()

    fun addReview(review: Review) {
        firestore.collection("reviews")
            .add(review)
            .addOnSuccessListener {
                // Review added successfully
            }
            .addOnFailureListener { e ->
                // Handle failure
            }
    }




}