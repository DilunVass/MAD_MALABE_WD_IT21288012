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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodshop.adapters.ReviewAdapter
import com.example.foodshop.adapters.favproductsadapter
import com.example.foodshop.adapters.viewPagerimages
import com.example.foodshop.data.Favourites
import com.example.foodshop.data.Review
import com.example.foodshop.databinding.FragmentProductBinding
import com.example.foodshop.util.Resource
import com.example.foodshop.viewModel.DetailsViewmodel
import com.example.foodshop.viewModel.ReviewDisplay
import com.example.foodshop.viewModel.ReviewViewModel
import kotlinx.coroutines.flow.collectLatest

class ProductDetailsFragment : Fragment(){
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private lateinit var binding: FragmentProductBinding
    private val ReviewViewModel : ReviewViewModel by viewModels()
    private val viewPagerAdaptor by lazy { viewPagerimages() }
    private val viewModel by viewModels<DetailsViewmodel>()
    private val ReviewDisplay by viewModels<ReviewDisplay>()
    private val ReviewAdapter by lazy { ReviewAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product
        setupViewpager()
        var reviewAdapter = ReviewAdapter()
        var ReviewViewModel = ViewModelProvider(this).get(com.example.foodshop.viewModel.ReviewViewModel::class.java)
        var  reviewDisplay = ViewModelProvider(this).get(com.example.foodshop.viewModel.ReviewDisplay::class.java)
        setupfavRv()
        binding.SubmitBTN.setOnClickListener {

            val comment = binding.Comment.text.toString().trim()

            // Validate user input
            if ( comment.isNotEmpty()) {
                val review = Review(product.id, comment)
                ReviewViewModel.addReview(review)
                Toast.makeText(requireContext(), "Review succesfully added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please provide a rating and comment", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnaddtofav.setOnClickListener{
            viewModel.addUpdateProductFavourite(Favourites(product))
        }
        lifecycleScope.launchWhenStarted {
            viewModel.Favourite.collectLatest {
                when(it){

                    is Resource.Success -> {
                        Toast.makeText(requireContext(), "Product added to the favourites", Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            ReviewDisplay.Revproducts.collectLatest {
                when(it){
                    is Resource.Loading -> {
                      //

                    }
                    is Resource.Success -> {

                        if(it.data!!.isEmpty()){
                            showEmptyfav()
                        }else{
                            hideEmptyfav()
                            ReviewAdapter.differ.submitList(it.data)
                        }

                    }
                    is Resource.Error -> {

                        Toast.makeText(requireContext(),it.message , Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        reviewDisplay.getRevProducts(product.id)


        binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = product.description
            tvProductPrice.text = "$ ${product.price}"
        }

        viewPagerAdaptor.differ.submitList(product.images)

    }

    private fun hideEmptyfav() {
        //TODO("Not yet implemented")
    }

    private fun showEmptyfav() {
      //  TODO("Not yet implemented")
    }

    private fun setupViewpager() {
       binding.apply {
          viewPagerProduct.adapter = viewPagerAdaptor
       }
    }
    private fun setupfavRv() {
        binding.rvCart.apply {
            layoutManager = LinearLayoutManager(requireContext() , RecyclerView.VERTICAL , false)
            adapter = ReviewAdapter
        }
    }
}
