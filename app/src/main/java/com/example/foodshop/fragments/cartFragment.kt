package com.example.foodshop2.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodshop.R
import com.example.foodshop.adapters.favproductsadapter
import com.example.foodshop.databinding.ActivityMainBinding
import com.example.foodshop.databinding.FragmentCartBinding
import com.example.foodshop.util.Resource
import com.example.foodshop.viewModel.FavviewModel
import kotlinx.coroutines.flow.collectLatest


class cartFragment : Fragment(R.layout.fragment_cart) {

    private lateinit var binding: FragmentCartBinding
    private val favproductsadapter by lazy { favproductsadapter() }
    private val viewModel by activityViewModels<FavviewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupfavRv()

        lifecycleScope.launchWhenStarted {
            viewModel.favProducts.collectLatest {
                when(it){
                    is Resource.Loading -> {
                        binding.progressbarCart.visibility = View.VISIBLE

                    }
                    is Resource.Success -> {
                        binding.progressbarCart.visibility = View.INVISIBLE
                        if(it.data!!.isEmpty()){
                            showEmptyfav()
                        }else{
                            hideEmptyfav()
                            favproductsadapter.differ.submitList(it.data)
                        }

                    }
                    is Resource.Error -> {
                        binding.progressbarCart.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(),it.message , Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun hideEmptyfav() {
        //TODO("Not yet implemented")
    }

    private fun showEmptyfav() {
        //TODO("Not yet implemented")
    }

    private fun setupfavRv() {
      binding.rvCart.apply {
          layoutManager = LinearLayoutManager(requireContext() , RecyclerView.VERTICAL , false)
          adapter = favproductsadapter
      }
    }


}