package com.rentateam.rentateam_test.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rentateam.rentateam_test.R
import com.rentateam.rentateam_test.databinding.FragmentUsersBinding
import com.rentateam.rentateam_test.domain.adapters.UserAdapter
import com.rentateam.rentateam_test.domain.entities.UserEntity
import com.rentateam.rentateam_test.domain.observer.Event
import com.rentateam.rentateam_test.domain.viewmodels.MainViewModel
import java.util.ArrayList
import android.os.Parcelable
import com.rentateam.rentateam_test.domain.utils.NetworkChecker


class UsersFragment: Fragment() {
    private lateinit var binding: FragmentUsersBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var sharedPref: SharedPreferences

    private val usersList: ArrayList<UserEntity> = ArrayList<UserEntity>()
    private lateinit var userAdapter: UserAdapter
    private var loadedPages = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        userAdapter = UserAdapter(usersList)
        viewModel.usersList.observe(viewLifecycleOwner, ::updateUsersList)
        viewModel.emptyData.observe(viewLifecycleOwner, ::showEmptyDataMessage)
        viewModel.savedUsersList.observe(viewLifecycleOwner, ::restoreUsersList)
        viewModel.networkError.observe(viewLifecycleOwner, ::showNetworkErrorMessage)
        userAdapter.loadMore.observe(viewLifecycleOwner, ::loadNewUsersPage)
        userAdapter.selectedUser.observe(viewLifecycleOwner, ::openUserPage)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        binding.rvUsers.layoutManager = LinearLayoutManager(context)
        binding.rvUsers.adapter = userAdapter
    }

    override fun onResume() {
        super.onResume()
        showLoadingIndicator(true)
        usersList.clear()
        if (!NetworkChecker.isNetworkAvailable(context)) {
            viewModel.loadUsersFromDb()
        } else {
            loadedPages = 0
            val editor = sharedPref.edit()
            editor.putInt("loadedPages", loadedPages)
            editor.apply()
            viewModel.loadUsers(loadedPages + 1)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.usersList.removeObservers(this)
        viewModel.emptyData.removeObservers(this)
        viewModel.savedUsersList.removeObservers(this)
        viewModel.networkError.removeObservers(this)
        userAdapter.loadMore.removeObservers(this)
        userAdapter.selectedUser.removeObservers(this)
        viewModel.onDestroy()
    }

    private fun updateUsersList(newUsersList : Event<List<UserEntity>>) {
        val content: List<UserEntity>? = newUsersList.getContentIfNotHandled()
        if (content != null) {
            showLoadingIndicator(false)
            loadedPages += 1
            val editor = sharedPref.edit()
            editor.putInt("loadedPages", loadedPages)
            editor.apply()
            usersList.addAll(content)
            userAdapter.notifyDataSetChanged()
        }
    }

    private fun restoreUsersList(newUsersList : Event<List<UserEntity>>) {
        val content: List<UserEntity>? = newUsersList.getContentIfNotHandled()
        if (content != null) {
            showLoadingIndicator(false)
            loadedPages = sharedPref.getInt("loadedPages", 0)
            usersList.addAll(content)
            userAdapter.notifyDataSetChanged()
        }
    }

    private fun openUserPage(userEntity: Event<UserEntity>) {
        val content: UserEntity? = userEntity.getContentIfNotHandled()
        if (content != null) {
            val bundle = Bundle()
            bundle.putSerializable("user", content)
            findNavController().navigate(R.id.action_usersFragment_to_userPageFragment, bundle)
        }
    }

    private fun loadNewUsersPage(loadMore: Event<Boolean>) {
        val content: Boolean? = loadMore.getContentIfNotHandled()
        if (content != null) {
            if(content) {
                viewModel.loadUsers( loadedPages + 1)
            }
        }
    }

    private fun showEmptyDataMessage(emptyData : Event<Boolean>) {
        val content: Boolean? = emptyData.getContentIfNotHandled()
        if (content != null) {
            if(content) {
                Toast.makeText(context, getString(R.string.empty_data), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showNetworkErrorMessage(networkError : Event<Boolean>) {
        val content: Boolean? = networkError.getContentIfNotHandled()
        if (content != null) {
            if(content) {
                Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showLoadingIndicator(isLoading : Boolean) {
        if (isLoading) {
            binding.pbLoadingUsers.visibility = View.VISIBLE
            binding.rvUsers.visibility = View.GONE
        } else {
            binding.pbLoadingUsers.visibility = View.GONE
            binding.rvUsers.visibility = View.VISIBLE
        }
    }
}