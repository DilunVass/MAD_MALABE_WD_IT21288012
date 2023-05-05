package com.example.foodshop.viewModel

import androidx.lifecycle.ViewModel
import com.example.foodshop.data.Product
import com.example.foodshop.data.Review
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.koin.core.inject

class RecipieAddviewmodel : ViewModel() , KoinComponent {
    private val firestore : FirebaseFirestore by inject()
    fun addRecipie(product: Product) {
        firestore.collection("Products")
            .add(product)
            .addOnSuccessListener {
                // Review added successfully
            }
            .addOnFailureListener { e ->
                // Handle failure
            }
    }



}