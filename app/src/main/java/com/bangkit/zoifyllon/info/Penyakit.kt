package com.bangkit.zoifyllon.info

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Penyakit (
    val name: String,
    val description: String,
    val photo: Int,
    val photobg: Int
    ): Parcelable
