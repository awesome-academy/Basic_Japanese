package com.sun.basic_japanese.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KanjiBasicMessage(
    val kanjiBasicList: List<KanjiBasic>,
    val currentPosition: Int
) : Parcelable
