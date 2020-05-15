package com.graphtech.giserap.network


import com.graphtech.giserap.model.DetailUserResponse
import com.graphtech.giserap.model.FollowResponse
import com.graphtech.giserap.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPIService {
    @GET("search/users?")
    fun getSearchUsers(
        @Query("q") q : String
    ) : Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username : String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollower(
        @Path("username") username : String
    ) : Call<List<FollowResponse>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ) : Call<List<FollowResponse>>
}