package com.example.foodshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodshop.databinding.ActivityMainBinding
import com.example.foodshop.util.Resource
import com.example.foodshop.viewModel.FavviewModel
import com.example.foodshop.viewModel.MainCategoryViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.collectLatest
import java.lang.reflect.Array.get


class MainActivity : AppCompatActivity() {
   private lateinit var model: MainCategoryViewModel

   val binding by lazy {
       ActivityMainBinding.inflate(layoutInflater)
   }

   val viewmodel by viewModels<FavviewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.shoppingHostFragment)
        binding.bottomNavigation.setupWithNavController(navController)

        lifecycleScope.launchWhenStarted {
            viewmodel.favProducts.collectLatest {
                when(it){
                    is Resource.Success -> {
                        val count = it.data?.size?: 0
                        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
                        bottomNavigation.getOrCreateBadge(R.id.cartFragment2).apply {
                            number = count
                            backgroundColor = resources.getColor(R.color.g_blue)
                        }

                    }
                    else -> Unit
                }
            }
        }

    }
}