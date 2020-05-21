package com.graphtech.giserap.model

data class Favorite(val id: Long?, val userId: String?, val userAvatar: String?) {

    companion object{
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val USER_ID: String = "USER_ID"
        const val USER_AVATAR: String = "USER_AVATAR"
    }
}