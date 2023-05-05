package com.example.foodshop.viewModel

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodshop.data.Favourites
import com.example.foodshop.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class DetailsViewmodel constructor(

):ViewModel() , KoinComponent{
    private val firestore : FirebaseFirestore by inject()
    private val _addToFavourite = MutableStateFlow<Resource<Favourites>>(Resource.Loading())
    val Favourite =_addToFavourite .asStateFlow()

    fun addUpdateProductFavourite(favourites: Favourites){
        viewModelScope.launch { _addToFavourite.emit(Resource.Loading()) }
        firestore.collection("favourites").whereEqualTo("product.id" , favourites.product.id).get().addOnSuccessListener {
            it.documents.let {
                if(it.isEmpty()){ // add new favourite
                    addNewProduct(favourites)

                }

            }
        }.addOnFailureListener{
            viewModelScope.launch { _addToFavourite.emit(Resource.Error(it.message.toString())) }

        }
    }

    private fun addNewProduct(favourites: Favourites){
        addProduct(favourites){addedProduct , e ->
            viewModelScope.launch {
                if(e == null)
                    _addToFavourite.emit(Resource.Success(addedProduct!!))

                else
                    _addToFavourite.emit(Resource.Error(e.message.toString()))
            }

        }
    }
    private val FavouritesCollection = firestore.collection("favourites")
    fun addProduct(favourites: Favourites, onResult: (Favourites?,java.lang.Exception?) ->Unit){
        FavouritesCollection.document().set(favourites).addOnSuccessListener {
            onResult(favourites , null)
        }.addOnFailureListener{
            onResult(null , it)
        }
    }


}
