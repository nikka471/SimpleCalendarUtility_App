//package com.poliarc.simplecalendarutilityapp.data.model
//
//
//data class Event(
//    val id: String = "",
//    val title: String = "",
//    val date: String = "",
//    val description: String = "",
//    val notes: String? = null  // user-added notes
//)

package com.poliarc.simplecalendarutilityapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val id: String = "",
    val title: String = "",
    val date: String = "",
    val description: String = "",
    val notes: String? = null  // user-added notes
) : Parcelable
