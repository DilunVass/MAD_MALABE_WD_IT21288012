package com.example.foodshop.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodshop.data.Product
import com.example.foodshop.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import org.koin.core.inject
import org.koin.core.KoinComponent

class MainCategoryViewModel : ViewModel() , KoinComponent {
    private val firestore : FirebaseFirestore by inject()

    private val _specialproducts = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val specialproducts : StateFlow<Resource<List<Product>>> = _specialproducts


    private val _bestDealsProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val bestDealsProducts: StateFlow<Resource<List<Product>>> = _bestDealsProducts


    private val _mostPopular = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val mostPopular  : StateFlow<Resource<List<Product>>> = _mostPopular
    init {
    fetchSpecialproducts()
        BestDealsproducts()
        MostPopularproducts()
}

   fun fetchSpecialproducts(){
       viewModelScope.launch {
           _specialproducts.emit(Resource.Loading())

       }
       firestore.collection("Products").whereEqualTo("category" , "Breakfast").get().addOnSuccessListener { result ->
           val specialproductslist = result.toObjects(Product :: class.java)
         viewModelScope.launch {
             _specialproducts.emit(Resource.Success(specialproductslist))
         }


       }.addOnFailureListener{
           viewModelScope.launch {
               _specialproducts.emit(Resource.Error(it.message.toString()))
           }

       }


   }

    fun BestDealsproducts(){
        viewModelScope.launch {
            _bestDealsProducts.emit(Resource.Loading())

        }
        firestore.collection("Products").whereEqualTo("category" , "Lunch").get().addOnSuccessListener { result ->
            val specialproductslist = result.toObjects(Product :: class.java)
            viewModelScope.launch {
                _bestDealsProducts.emit(Resource.Success(specialproductslist))
            }


        }.addOnFailureListener{
            viewModelScope.launch {
                _bestDealsProducts.emit(Resource.Error(it.message.toString()))
            }

        }


    }


    fun MostPopularproducts(){
        viewModelScope.launch {
            _mostPopular.emit(Resource.Loading())

        }
        firestore.collection("Products").get().addOnSuccessListener { result ->
            val specialproductslist = result.toObjects(Product :: class.java)
            viewModelScope.launch {
                _mostPopular.emit(Resource.Success(specialproductslist))
            }


        }.addOnFailureListener{
            viewModelScope.launch {
                _mostPopular.emit(Resource.Error(it.message.toString()))
            }

        }


    }
}