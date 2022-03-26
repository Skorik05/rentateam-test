package com.rentateam.rentateam_test.domain.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.rentateam.rentateam_test.R
import com.rentateam.rentateam_test.domain.entities.UserEntity
import com.rentateam.rentateam_test.domain.observer.Event

class UserAdapter(private val usersList: List<UserEntity>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val mutableLoadMore : MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    var loadMore : LiveData<Event<Boolean>> = mutableLoadMore

    private val mutableSelectedUser : MutableLiveData<Event<UserEntity>> = MutableLiveData<Event<UserEntity>>()
    var selectedUser : LiveData<Event<UserEntity>> = mutableSelectedUser

    private object VIEW_TYPES {
        const val NORMAL = 0
        const val FOOTER = 1
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == usersList.size - 1) {
            VIEW_TYPES.FOOTER
        } else {
            VIEW_TYPES.NORMAL
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserViewHolder {
        return when(i) {
            VIEW_TYPES.FOOTER-> {
                UserLoadMoreViewHolder(
                    LayoutInflater.from(viewGroup.context).
                        inflate(R.layout.item_user_card_load_more, viewGroup, false)
                )
            }
            else -> {
                UserViewHolder(
                    LayoutInflater.from(viewGroup.context).
                        inflate(R.layout.item_user_card, viewGroup, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    open inner class UserViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {
        open fun bind(userEntity: UserEntity) {
            view.findViewById<AppCompatTextView>(R.id.tvUseId).text = userEntity.id.toString()
            view.findViewById<AppCompatTextView>(R.id.tvFullName).text = view.context.getString(R.string.user_full_name, userEntity.lastName, userEntity.firstName)
            view.rootView.setOnClickListener {
                mutableSelectedUser.postValue(Event(userEntity))
            }
        }
    }

    inner class UserLoadMoreViewHolder(private val view : View) : UserViewHolder(view) {
        override fun bind(userEntity: UserEntity) {
            view.findViewById<AppCompatTextView>(R.id.tvUseId).text = userEntity.id.toString()
            view.findViewById<AppCompatTextView>(R.id.tvFullName).text = view.context.getString(R.string.user_full_name, userEntity.lastName, userEntity.firstName)
            view.findViewById<Button>(R.id.bLoadMore).setOnClickListener {
                mutableLoadMore.postValue(Event(true))
            }
            view.rootView.setOnClickListener {
                mutableSelectedUser.postValue(Event(userEntity))
            }
        }
    }
}