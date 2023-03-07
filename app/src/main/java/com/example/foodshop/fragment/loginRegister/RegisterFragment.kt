package com.example.foodshop.fragment.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.foodshop.R
import com.example.foodshop.data.User
import com.example.foodshop.databinding.FragmentRegisterBinding
import com.example.foodshop.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register){
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
          buttonRegister.setOnClickListener{
              val user = User(
                  name.text.toString().trim(),
                  lastname.text.toString().trim(),
                  email.text.toString().trim()

              )
              val password = password.text.toString()
              viewModel.createAccountwithEmailPassword(user, password)
          }
        }
    }
}