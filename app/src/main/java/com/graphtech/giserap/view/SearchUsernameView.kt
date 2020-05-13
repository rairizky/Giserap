package com.graphtech.giserap.view

import com.graphtech.giserap.model.SearchItem

interface SearchUsernameView {
    fun onShowLoading()
    fun onHideLoading()
    fun onShowNothing(message: String)
    fun onHideNothing()
    fun onSuccessGetSearch(data: List<SearchItem?>?)
    fun onErrorGetSearch(message: String)
}