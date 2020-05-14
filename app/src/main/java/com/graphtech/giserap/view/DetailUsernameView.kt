package com.graphtech.giserap.view

import com.graphtech.giserap.model.DetailUserResponse
import com.graphtech.giserap.model.SearchResponse

interface DetailUsernameView {
    fun onShowLoading()
    fun onHideLoading()
    fun onSuccessGetDetailUsername(response: DetailUserResponse?)
    fun onErrorGetDetailUsername(message: String)
}