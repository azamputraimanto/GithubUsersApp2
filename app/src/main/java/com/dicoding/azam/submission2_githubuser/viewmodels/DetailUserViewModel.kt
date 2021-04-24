package com.dicoding.azam.submission2_githubuser.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.azam.submission2_githubuser.models.DetailUser
import com.dicoding.azam.submission2_githubuser.utils.UserRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {

    val user = MutableLiveData<DetailUser>()

    fun setUsersDetail(username: String) {
        UserRetrofit.apiInstance
            .getDetailUser(username)
            .enqueue(object  : Callback<DetailUser> {
                override fun onResponse(
                    call: Call<DetailUser>,
                    response: Response<DetailUser>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getUsersUsers() : LiveData<DetailUser> {
        return user
    }
}