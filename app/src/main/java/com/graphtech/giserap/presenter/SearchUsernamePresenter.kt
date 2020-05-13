package com.graphtech.giserap.presenter

import com.graphtech.giserap.model.SearchResponse
import com.graphtech.giserap.network.NetworkConfig
import com.graphtech.giserap.view.SearchUsernameView
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUsernamePresenter(val searchUsernameView: SearchUsernameView) {
    fun getSearchResult(querySearch: String?) {
        searchUsernameView.onShowLoading()
        NetworkConfig.service()
            .getSearchUsers(querySearch.toString())
            .enqueue(object: Callback<SearchResponse>{
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    searchUsernameView.onHideLoading()
                    searchUsernameView.onErrorGetSearch(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.items?.size == 0) {
                            searchUsernameView.onHideLoading()
                            searchUsernameView.onShowNothing("Username '${querySearch}' not found!")
                        }else {
                            searchUsernameView.onHideNothing()
                            searchUsernameView.onHideLoading()
                            searchUsernameView.onSuccessGetSearch(response.body()?.items)
                        }
                    }
                }
            })
    }
}