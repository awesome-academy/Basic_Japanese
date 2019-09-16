package com.sun.basic_japanese.data.model

import android.os.Parcelable
import com.sun.basic_japanese.constants.BasicJapaneseConstants.HIRAGANA
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FlashCardMessage(
    val alphabet: Alphabet? = null,
    val type: String = HIRAGANA
) : Parcelable
