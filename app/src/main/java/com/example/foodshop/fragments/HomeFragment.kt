package com.example.foodshop2.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodshop.R
import com.example.foodshop.adapters.HomeViewPagerAdaptor
import com.example.foodshop.databinding.FragmentHomeBinding
import com.example.foodshop.fragments.categories.BreakfastFragment
import com.example.foodshop.fragments.categories.DinnerFragment
import com.example.foodshop.fragments.categories.LunchFragment
import com.example.foodshop.fragments.categories.MainCategoryFragment
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragment = arrayListOf<Fragment>(
             MainCategoryFragment(),
            BreakfastFragment(),
            DinnerFragment(),
            LunchFragment()

        )

        val viewPager2Adaptor = HomeViewPagerAdaptor(categoriesFragment , childFragmentManager , lifecycle)
        binding.viewpager.adapter = viewPager2Adaptor

        TabLayoutMediator(binding.tablayout , binding.viewpager){
            tab , position ->
            when(position){
                0 -> tab.text = "Home"
                1-> tab.text = "Breakfast"
                2 -> tab.text = "Lunch"
                3-> tab.text = "Night"
            }
        }.attach()
    }

}