package com.example.oncash.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.oncash.R
import com.example.oncash.ViewModel.home_viewModel
import com.example.oncash.databinding.FragmentProfileFragmentBinding




class profile_fragment : Fragment() {

    lateinit var homeViewmodel: home_viewModel
    lateinit var binding : FragmentProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileFragmentBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewmodel = activity.run{
            ViewModelProvider(this!!)[homeViewmodel::class.java]
        }

    }

}