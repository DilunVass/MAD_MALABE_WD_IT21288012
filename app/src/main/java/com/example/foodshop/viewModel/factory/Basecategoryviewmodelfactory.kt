package com.example.foodshop.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodshop.data.Category
import com.example.foodshop.viewModel.CategoryviewModel
import com.google.firebase.firestore.FirebaseFirestore

class Basecategoryviewmodelfactory (
    private val firestore: FirebaseFirestore,
    private val category: Category
        ) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryviewModel(firestore,category) as T
    }

}
