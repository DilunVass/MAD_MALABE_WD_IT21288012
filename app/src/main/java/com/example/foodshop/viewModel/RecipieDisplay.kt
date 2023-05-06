package com.example.foodshop.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodshop.data.Product
import com.example.foodshop.data.Review
import com.example.foodshop.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class RecipieDisplay :ViewModel() , KoinComponent {
    private val firestore : FirebaseFirestore by inject()

    private val _products = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val products = _products.asStateFlow()

    fun getRecipies(){
        viewModelScope.launch {
            _products.emit(Resource.Loading())
        }

        firestore.collection("Products").addSnapshotListener{


                value , error ->
            if(error != null || value == null){
                viewModelScope.launch {  _products.emit(Resource.Error(error?.message.toString())) }
            }
            else{
                val products  = value.toObjects(Product :: class.java)
                viewModelScope.launch { _products.emit(Resource.Success( products ))}
            }
        }
    }
}