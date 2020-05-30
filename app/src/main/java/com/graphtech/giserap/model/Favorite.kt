package com.graphtech.giserap.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite(
    var id: Int = 0,
    var username: String? = null,
    var avatar: String? = null
) : Parcelable