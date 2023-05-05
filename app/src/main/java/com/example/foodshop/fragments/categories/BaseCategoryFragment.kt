package com.example.foodshop.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodshop.R
import com.example.foodshop.adapters.BestDealsAdapter
import com.example.foodshop.adapters.MostPopularAdapter
import com.example.foodshop.databinding.FragmentBaseCategoryBinding
import org.koin.android.ext.android.bind


open class BaseCategoryFragment : Fragment(R.layout.fragment_base_category) {
private lateinit var binding: FragmentBaseCategoryBinding
protected val mostpopular : MostPopularAdapter by lazy { MostPopularAdapter()}


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMostPopularRV()

        mostpopular.onClick = {
            val b = Bundle().apply { putParcelable("product" , it) }
            findNavController().navigate(R.id.action_homeFragment2_to_productDetailsFragment,b)
        }
        binding.MostPopularCat.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(!recyclerView.canScrollVertically(1)&&dx != 0){
                    onMostpopularRequest()
                }
            }
        })


    }
    open fun onMostpopularRequest(){

    }


    private fun setupMostPopularRV() {

        binding.MostPopularCat.apply {
            layoutManager = GridLayoutManager(requireContext() ,1,GridLayoutManager.VERTICAL , false)
            adapter =    mostpopular
        }
    }
}