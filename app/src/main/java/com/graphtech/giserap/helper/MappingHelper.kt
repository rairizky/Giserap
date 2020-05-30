package com.graphtech.giserap.helper

import android.database.Cursor
import com.graphtech.giserap.model.Favorite

object MappingHelper {

    fun mapCursorToArrayList(favoriteCursor: Cursor?) : ArrayList<Favorite> {
        val favoriteList = ArrayList<Favorite>()

        favoriteCursor?.apply {
            while(moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR))
                favoriteList.add(Favorite(id, username, avatar))
            }
        }
        return favoriteList
    }
}