package com.sun.basic_japanese.data.model

import android.os.Parcelable
import com.sun.basic_japanese.constants.BasicJapaneseConstants.HIRAGANA
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FlashCardsMessage(
    val alphabet: List<Alphabet?> = listOf(),
    val currentPosition: Int = 0,
    val type: String = HIRAGANA
) : Parcelable
