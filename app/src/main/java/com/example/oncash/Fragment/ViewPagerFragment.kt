package com.example.oncash.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.oncash.Component.onboarding.ViewPagerAdapter
import com.example.oncash.Fragment.screens.FirstScreen
import com.example.oncash.Fragment.screens.SecondScreen
import com.example.oncash.Fragment.screens.ThirdScreen

import com.example.oncash.databinding.FragmentViewPagerBinding


class ViewPagerFragment : Fragment() {
    private lateinit var binding : FragmentViewPagerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerBinding.inflate(inflater , container , false)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter
        return view
    }
}