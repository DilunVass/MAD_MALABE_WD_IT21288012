package com.example.foodshop2.fragments.shopping

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.foodshop.R
import com.example.foodshop.data.Product
import com.example.foodshop.data.Review
import com.example.foodshop.databinding.FragmentProductBinding
import com.example.foodshop.databinding.FragmentProfileBinding
import com.example.foodshop.fragments.MyrecipieFragment
import com.example.foodshop.viewModel.RecipieAddviewmodel
import java.util.*

//Recipie management
class profileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var selectedImages = mutableListOf<Uri>()


        var RecipieAddviewmodel = ViewModelProvider(this).get(com.example.foodshop.viewModel.RecipieAddviewmodel::class.java)

        val selectImagesActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data

                    //Multiple images selected
                    if (intent?.clipData != null) {
                        val count = intent.clipData?.itemCount ?: 0
                        (0 until count).forEach {
                            val imagesUri = intent.clipData?.getItemAt(it)?.uri
                            imagesUri?.let { selectedImages.add(it) }
                        }

                        //One images was selected
                    } else {
                        val imageUri = intent?.data
                        imageUri?.let { selectedImages.add(it) }
                    }

                }
            }
        //6
        binding.imagess.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.type = "image/*"
            selectImagesActivityResult.launch(intent)
        }
binding.list.setOnClickListener{
    navigateToAnotherFragment()
}


        binding.submitrecipie.setOnClickListener {



            val name = binding.edname.text.toString().trim()
            val category = binding.edcat.text.toString().trim()
            val description = binding.eddesc.text.toString().trim()
            val price = binding.edprice.text.toString().trim()
            val images = mutableListOf<String>()
            val selectedColors = mutableListOf<Int>()
            val  sizes = mutableListOf<String>()
            val offerPercentage = "11"

            // Validate user input
            if ( name.isNotEmpty()) {
                val product = Product(
                    UUID.randomUUID().toString(),
                    name,
                    category,
                    price.toFloat(),
                    if (offerPercentage.isEmpty()) null else offerPercentage.toFloat(),
                    selectedColors,
                    if (description.isEmpty()) null else description,
                    sizes,
                    images
                )

                RecipieAddviewmodel.addRecipie(product)
                Toast.makeText(requireContext(), "product succesfully added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please provide a rating and comment", Toast.LENGTH_SHORT).show()
            }
        }



    }

    private fun navigateToAnotherFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragment = MyrecipieFragment()


        fragmentTransaction.replace(R.id.ttt, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}
