package com.graphtech.giserap.view

import com.graphtech.giserap.model.FollowResponse

interface FollowView {
    fun onShowLoading()
    fun onHideLoading()
    fun onSuccessFollow(data: List<FollowResponse?>?)
    fun onErrorFollower(message: String)
}