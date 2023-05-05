package com.example.foodshop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val id: String,
    val review : String,




):Parcelable{
    constructor():this("","")
}
