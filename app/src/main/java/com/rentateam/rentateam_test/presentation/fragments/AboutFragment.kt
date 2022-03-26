package com.rentateam.rentateam_test.presentation.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.rentateam.rentateam_test.databinding.FragmentAboutBinding
import com.rentateam.rentateam_test.databinding.FragmentUsersBinding
import com.rentateam.rentateam_test.domain.adapters.UserAdapter
import com.rentateam.rentateam_test.domain.entities.UserEntity
import com.rentateam.rentateam_test.domain.viewmodels.MainViewModel
import java.util.ArrayList

class AboutFragment: Fragment() {
    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }
}