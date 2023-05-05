package com.example.foodshop.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodshop.data.Favourites
import com.example.foodshop.data.Product
import com.example.foodshop.data.Review
import com.example.foodshop.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class ReviewDisplay():ViewModel(), KoinComponent{
    private val firestore : FirebaseFirestore by inject()

    private val _Revproducts = MutableStateFlow<Resource<List<Review>>>(Resource.Loading())
    val Revproducts = _Revproducts.asStateFlow()




     fun getRevProducts(productid : String){
        viewModelScope.launch {
            _Revproducts.emit(Resource.Loading())
        }

        firestore.collection("reviews").whereEqualTo("id" ,productid ).addSnapshotListener{
//            val reviews = it.toObjects(Review::class.java)
//            viewModelScope.launch {
//                _Revproducts.emit(Resource.Success(reviews ))
//            }
//        }.addOnFailureListener{
//            viewModelScope.launch {
//                _Revproducts.emit(Resource.Error(it.message.toString()))
//            }
//        }

                value , error ->
            if(error != null || value == null){
                viewModelScope.launch {  _Revproducts.emit(Resource.Error(error?.message.toString())) }
            }
            else{
                val Revproducts  = value.toObjects(Review :: class.java)
                viewModelScope.launch {  _Revproducts.emit(Resource.Success(Revproducts ))}
            }
        }
    }


}