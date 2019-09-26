package com.sun.basic_japanese.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KanjiAdvanceMessage(
    val kanjiAdvanceList: List<KanjiAdvance>,
    val currentPosition: Int
) : Parcelable
