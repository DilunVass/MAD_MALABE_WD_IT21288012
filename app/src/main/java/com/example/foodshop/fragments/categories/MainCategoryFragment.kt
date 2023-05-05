package com.example.foodshop.fragments.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodshop.R
import com.example.foodshop.adapters.BestDealsAdapter
import com.example.foodshop.adapters.MostPopularAdapter
import com.example.foodshop.adapters.SpecialProductsAdapter
import com.example.foodshop.databinding.FragmentMainCategoryBinding
import com.example.foodshop.util.Resource
import com.example.foodshop.viewModel.MainCategoryViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.bind

private val TAG = "MainCategoryFragment "
class MainCategoryFragment : Fragment(R.layout.fragment_main_category) {
    private lateinit var binding : FragmentMainCategoryBinding
    private lateinit var specialProductsAdapter: SpecialProductsAdapter
    private lateinit var bestDealsAdapter: BestDealsAdapter
    private lateinit var MostPopularAdapter : MostPopularAdapter
    private val viewModel by viewModels<MainCategoryViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpecialProducts()
        setupBestDeals()
        setupMostPopular()

        specialProductsAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product" , it) }
            findNavController().navigate(R.id.action_homeFragment2_to_productDetailsFragment,b)
        }
        bestDealsAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product" , it) }
            findNavController().navigate(R.id.action_homeFragment2_to_productDetailsFragment,b)
        }
        MostPopularAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product" , it) }
            findNavController().navigate(R.id.action_homeFragment2_to_productDetailsFragment,b)
        }
        lifecycleScope.launchWhenStarted {
            viewModel.specialproducts.collectLatest {
                when (it){
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        specialProductsAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error -> {
                        hideLoading()
                        Log.e(TAG , it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.bestDealsProducts.collectLatest {
                when (it){
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        bestDealsAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error -> {
                        hideLoading()
                        Log.e(TAG , it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.mostPopular.collectLatest {
                when (it){
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                       MostPopularAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error -> {
                        hideLoading()
                        Log.e(TAG , it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupMostPopular() {
       bestDealsAdapter =  BestDealsAdapter()
        binding.mostPopular.apply {
            layoutManager = GridLayoutManager(requireContext() ,1,GridLayoutManager.VERTICAL , false)
            adapter =bestDealsAdapter
        }

    }

    private fun setupBestDeals() {
        MostPopularAdapter = MostPopularAdapter()
        binding.BestFooddeals.apply {
            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)
            adapter =  MostPopularAdapter
        }
    }

    private fun hideLoading() {
     binding.Maincategoryprogress.visibility = View.GONE
    }

    private fun showLoading() {
        binding.Maincategoryprogress.visibility = View.VISIBLE
    }

    private fun setupSpecialProducts() {
        specialProductsAdapter = SpecialProductsAdapter()
        binding.specialWakers.apply {
            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)
            adapter = specialProductsAdapter
        }
    }
}