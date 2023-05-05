package com.example.foodshop.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodshop.data.Category
import com.example.foodshop.data.Product
import com.example.foodshop.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryviewModel constructor(
    private val firestore: FirebaseFirestore,
    private val category : Category
) :ViewModel(){

    private val _Mostpopular = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val MostPopular = _Mostpopular .asStateFlow()

    init {
        fetchMostPopular()
    }

    fun fetchMostPopular(){
        viewModelScope.launch {
            _Mostpopular.emit(Resource.Loading())
        }
        firestore.collection("Products").whereEqualTo("category" ,category.category).get().addOnSuccessListener {
            val products = it.toObjects(Product::class.java)
            viewModelScope.launch {
                _Mostpopular.emit(Resource.Success(products))
            }
        }.addOnFailureListener{
            viewModelScope.launch {
                _Mostpopular.emit(Resource.Error(it.message.toString()))
            }
        }

    }
}