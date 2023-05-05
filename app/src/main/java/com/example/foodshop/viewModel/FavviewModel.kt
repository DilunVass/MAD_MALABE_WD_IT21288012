package com.example.foodshop.viewModel

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

class FavviewModel : ViewModel() , KoinComponent {
    private val firestore : FirebaseFirestore by inject()
    private val _favProducts = MutableStateFlow<Resource<List<Favourites>>>(Resource.Loading())
    val favProducts = _favProducts.asStateFlow()
init {
    getFavProducts()
}


    private fun getFavProducts(){
        viewModelScope.launch { _favProducts.emit(Resource.Loading()) }
        firestore.collection("favourites").addSnapshotListener{
            value , error ->
            if(error != null || value == null){
                viewModelScope.launch { _favProducts.emit(Resource.Error(error?.message.toString())) }
            }
            else{
                val favProducts = value.toObjects(Favourites :: class.java)
                viewModelScope.launch { _favProducts.emit(Resource.Success(favProducts))}
            }
        }
    }


}