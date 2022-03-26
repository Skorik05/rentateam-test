package com.rentateam.rentateam_test.domain.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rentateam.rentateam_test.domain.entities.UserEntity
import com.rentateam.rentateam_test.domain.entities.UsersPageEntity
import com.rentateam.rentateam_test.domain.observer.Event
import com.rentateam.rentateam_test.model.Repository
import com.rentateam.rentateam_test.model.api.ApiHelper
import com.rentateam.rentateam_test.model.api.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val mutableUsersList = MutableLiveData<Event<List<UserEntity>>>()
    var usersList : LiveData<Event<List<UserEntity>>> = mutableUsersList

    private val mutableSavedUsersList = MutableLiveData<Event<List<UserEntity>>>()
    var savedUsersList : LiveData<Event<List<UserEntity>>> = mutableSavedUsersList

    private val mutableEmptyData = MutableLiveData<Event<Boolean>>()
    var emptyData : LiveData<Event<Boolean>> = mutableEmptyData

    private val mutableNetworkError = MutableLiveData<Event<Boolean>>()
    var networkError : LiveData<Event<Boolean>> = mutableNetworkError

    private val repo = Repository(ApiHelper(RetrofitBuilder.apiService))
    private val parentJob = Job()

    private val compositeDisposable = CompositeDisposable()

    fun loadUsers(page : Int) {
        val disposable  = repo.getUsers(page)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response : Response<UsersPageEntity>? ->
                    val body = response?.body()
                    if (body != null) {
                        if (!body.data.isEmpty()) {
                            if (page == 1) {
                                repo.deleteAllUsers()
                            }
                            repo.addUsersToDb(body.data)
                            mutableUsersList.postValue(Event(body.data))
                        } else {
                            mutableEmptyData.postValue(Event(true))
                        }
                    }
                },
                { throwable: Throwable? ->
                    if (throwable != null) {
                        mutableNetworkError.postValue(Event((true)))
                        Log.d("LoadUsersError", throwable.message.toString())
                    }
                }
            )
        compositeDisposable.add(disposable)
    }

    fun loadUsersFromDb() {
        val disposable  = repo.getAllUsersFromDb()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe(
                { users : List<UserEntity>? ->
                    if (users != null) {
                        mutableSavedUsersList.postValue(Event(users))
                    }
                },
                { throwable: Throwable? ->
                    if (throwable != null) {
                        mutableNetworkError.postValue(Event((true)))
                        Log.d("LoadUsersFromDbError", throwable.message.toString())
                    }
                }
            )
        if (disposable != null) {
            compositeDisposable.add(disposable)
        }
    }
    fun onDestroy() {
        compositeDisposable.clear()
    }

}