package com.rentateam.rentateam_test.presentation.fragments

import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.rentateam.rentateam_test.R
import com.rentateam.rentateam_test.databinding.FragmentUserPageBinding
import com.rentateam.rentateam_test.databinding.FragmentUsersBinding
import com.rentateam.rentateam_test.domain.adapters.UserAdapter
import com.rentateam.rentateam_test.domain.entities.UserEntity
import com.rentateam.rentateam_test.domain.viewmodels.MainViewModel
import java.util.ArrayList

class UserPageFragment : Fragment() {
    private lateinit var binding: FragmentUserPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{
            val userEntity = it.getSerializable("user") as UserEntity?
            userEntity?.let {user ->
                binding.tvName.text = getString(R.string.user_full_name, user.lastName, user.firstName)
                binding.tvEmail.text = getString(R.string.user_email, user.email)
                Glide.with(this)
                    .load(user.avatar)
                    .apply(
                        RequestOptions()
                            .error(R.drawable.avatar_placeholder)
                            .centerCrop()
                    )
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_LONG).show()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                    })
                    .into(binding.ivAvatar)
            }
        }

    }

}