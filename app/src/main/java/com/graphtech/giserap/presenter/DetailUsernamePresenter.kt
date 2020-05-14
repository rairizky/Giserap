package com.graphtech.giserap.presenter

import com.graphtech.giserap.model.DetailUserResponse
import com.graphtech.giserap.network.NetworkConfig
import com.graphtech.giserap.view.DetailUsernameView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUsernamePresenter(val detailUsernameView: DetailUsernameView) {
    fun getDetailUsername(username: String?) {
        detailUsernameView.onShowLoading()
        NetworkConfig.service()
            .getDetailUser(username.toString())
            .enqueue(object: Callback<DetailUserResponse> {
                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    detailUsernameView.onErrorGetDetailUsername(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        detailUsernameView.onHideLoading()
                        detailUsernameView.onSuccessGetDetailUsername(response.body())
                    }
                }

            })
    }
}