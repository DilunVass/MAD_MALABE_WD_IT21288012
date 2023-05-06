package com.example.foodshop.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodshop.adapters.MYRecipieadapter
import com.example.foodshop.adapters.ReviewAdapter
import com.example.foodshop.databinding.FragmentProductBinding
import com.example.foodshop.databinding.MyrecipieBinding
import com.example.foodshop.util.Resource
import com.example.foodshop.viewModel.RecipieDisplay
import com.example.foodshop.viewModel.ReviewDisplay
import kotlinx.coroutines.flow.collectLatest

class MyrecipieFragment : Fragment(){
    private lateinit var binding: MyrecipieBinding
    private val RecipieDisplay by viewModels<RecipieDisplay>()
    private val MYRecipieadapter by lazy { MYRecipieadapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyrecipieBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var  recipiedisplay = ViewModelProvider(this).get(com.example.foodshop.viewModel.RecipieDisplay::class.java)

        setupfavRv()

        lifecycleScope.launchWhenStarted {
            RecipieDisplay. products.collectLatest {
                when(it){
                    is Resource.Loading -> {
                        //

                    }
                    is Resource.Success -> {

                        if(it.data!!.isEmpty()){
                            showEmptyfav()
                        }else{
                            hideEmptyfav()
                            MYRecipieadapter.differ.submitList(it.data)
                        }

                    }
                    is Resource.Error -> {

                        Toast.makeText(requireContext(),it.message , Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
        recipiedisplay.getRecipies()

    }

    private fun hideEmptyfav() {
        //TODO("Not yet implemented")
    }

    private fun showEmptyfav() {
       // TODO("Not yet implemented")
    }

    private fun setupfavRv() {
        binding.rvrecipie.apply {
            layoutManager = LinearLayoutManager(requireContext() , RecyclerView.VERTICAL , false)
            adapter = MYRecipieadapter
        }
    }


}