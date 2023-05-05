package com.example.foodshop.fragments.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.foodshop.data.Category
import com.example.foodshop.util.Resource
import com.example.foodshop.viewModel.CategoryviewModel
import com.example.foodshop.viewModel.factory.Basecategoryviewmodelfactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import org.koin.core.inject

class BreakfastFragment : BaseCategoryFragment()  {
    private val firestore : FirebaseFirestore by inject()

    val viewmodel by viewModels<CategoryviewModel> {
        Basecategoryviewmodelfactory(firestore , Category.Breakfast)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launchWhenStarted {
            viewmodel.MostPopular.collectLatest {
                when(it){
                    is Resource.Loading ->{

                    }
                    is Resource.Success ->{
                        mostpopular.differ.submitList(it.data)

                    }
                    is Resource.Error -> {
                        Snackbar.make(requireView(),it.message.toString(),Snackbar.LENGTH_LONG).show()

                    }
                    else -> Unit
                }
            }
        }


    }
}