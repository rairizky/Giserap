package com.graphtech.giserap.network


import com.graphtech.giserap.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPIService {
    @GET("search/users?")
    fun getSearchUsers(
        @Query("q") q : String
    ) : Call<SearchResponse>
}